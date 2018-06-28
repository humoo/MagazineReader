package com.hyc.libs.http;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.hyc.libs.Configure;
import com.hyc.libs.http.callback.HttpCallBackListener;
import com.hyc.libs.utils.ActivityManager;
import com.hyc.libs.utils.JsonUtil;
import com.hyc.libs.utils.rxtool.RxToast;
import com.hyc.libs.utils.scalable.SharePrefUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author zhxumao
 * Created on 2017/12/28 0028 10:47.
 */

public class HttpHelper<T> {

    private volatile static HttpHelper instance;

    private HttpCallBackListener<String> httpCallBackListener;

    public HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    /**
     * POST
     *
     * @param url      url
     * @param map      参数
     * @param listener 回调函数
     */
    public void post(String url, @NonNull Map<String, Object> map, HttpCallBackListener<String> listener) {
        post(-1, null, url, map, null, listener);
    }

    /**
     * POST
     *
     * @param requestCode 发起请求的对象标识
     * @param tag         tag
     * @param url         url
     * @param map         参数
     * @param listener    回调函数
     */
    public void post(int requestCode, Object tag, String url, @NonNull Map<String, Object> map, HttpCallBackListener<String> listener) {
        post(requestCode, tag, url, map, null, listener);
    }

    /**
     * POST
     *
     * @param requestCode 发起请求的对象标识
     * @param tag         tag
     * @param url         url
     * @param paramsMap   参数
     * @param headerMap   头部信息
     * @param listener    回调函数
     */
    public void post(final int requestCode, Object tag, String url, @NonNull Map<String, Object> paramsMap, Map<Object, Object> headerMap, final HttpCallBackListener<String> listener) {

        PostRequest<String> postRequest = OkGo.post(url);

        postRequest.tag(tag);
        if (headerMap != null) {
            addHeader(postRequest, headerMap);
        }
        for (String key : paramsMap.keySet()) {
            postRequest.params(key, Configure.encode ? URLEncoder.encode(paramsMap.get(key).toString()) : paramsMap.get(key).toString());
        }
        postRequest.execute(new StringCallback() {

                                @Override
                                public void onSuccess(Response<String> response) {
                                    if (listener != null) {
                                        if (loginVerify(response)) return;
                                        listener.onSuccess(response, requestCode);
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    if (listener != null) {
                                        listener.onError(response, requestCode);
                                    }
                                }
                            }
        );
    }

    /**
     * GET
     *
     * @param url      url
     * @param map      参数
     * @param listener 回调函数
     */

    public void get(String url, @NonNull Map<String, Object> map, HttpCallBackListener<String> listener) {
        get(-1, null, url, map, null, listener);
    }

    /**
     * GET
     *
     * @param requestCode 发起请求的对象标识
     * @param tag         tag
     * @param url         url
     * @param map         参数
     * @param listener    回调函数
     */
    public void get(int requestCode, Object tag, String url, @NonNull Map<String, Object> map, HttpCallBackListener<String> listener) {
        get(requestCode, tag, url, map, null, listener);
    }

    /**
     * GET
     *
     * @param requestCode 发起请求的对象标识
     * @param tag         tag
     * @param url         url
     * @param paramsMap   参数
     * @param headerMap   头部信息
     * @param listener    回调函数
     */
    public void get(final int requestCode, Object tag, String url, @NonNull Map<String, Object> paramsMap, Map<Object, Object> headerMap, final HttpCallBackListener<String> listener) {
        url = appendParams(url, paramsMap);
        GetRequest<String> getRequest = OkGo.get(url);
        getRequest.tag(tag);
        if (headerMap != null) {
            addHeader(getRequest, headerMap);
        }
        getRequest.execute(new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                if (listener != null) {
                    if (loginVerify(response)) return;
                    listener.onSuccess(response, requestCode);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (listener != null) {
                    listener.onError(response, requestCode);
                }
            }
        });
    }

    /**
     * 登录验证
     *
     * @param response
     *
     * @return
     */
    private boolean loginVerify(Response<String> response) {
        String json = response.body();
        try {
            JSONObject object = new JSONObject(json);
            int error = JsonUtil.GetIntDefault(object, "error");
            if (error == 2 || error == 3) {
                RxToast.normal("登录验证已失效，请重新登录");
                SharePrefUtil.remove("IS_LOGIN");
                SharePrefUtil.remove("TOKEN");
                Activity activity = ActivityManager.getInstance().currentActivity();
                if (activity != null) {
                    String activityName = "com.hyc.hengran.mvp.login.view.VerifyActivity";
                    ActivityManager.getInstance().finishAllActivity();
                    Class clazz = Class.forName(activityName);
                    Intent intent = new Intent(activity, clazz);
                    activity.startActivity(intent);
                }
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 直接下载一个文件
     *
     * @param url
     * @param fileCallback
     */
    public void download(String url, FileCallback fileCallback) {
        download(null, url, fileCallback);
    }

    /**
     * 直接下载一个文件
     *
     * @param tag
     * @param url
     * @param fileCallback
     */
    public void download(Object tag, String url, FileCallback fileCallback) {
        OkGo.<File>get(url)
                .tag(tag)
                .execute(fileCallback);
    }

    /**
     * 上传单个文件
     * https://github.com/jeasonlzy/okhttp-OkGo/wiki/OkGo#8上传文件
     *
     * @param tag
     * @param url
     * @param key      文件key，可自定义
     * @param file     文件
     * @param listener
     */
    public void upload(Object tag, final int code, String url, String key, File file, final HttpCallBackListener<String> listener) {
        OkGo.<String>post(url)
                .tag(tag)
                .params(key, file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (listener != null) {
                            listener.onSuccess(response, code);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (listener != null) {
                            listener.onError(response, code);
                        }
                    }
                });
    }

    /**
     * 根据tag取消请求任务
     *
     * @param tag
     */
    public void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 添加头信息
     *
     * @param request
     * @param _header
     */
    private Request addHeader(Request request, Map<Object, Object> _header) {
        for (Object key : _header.keySet()) {
            request.headers(key.toString(), _header.get(key).toString());
        }
        return request;
    }

    /**
     * GET 参数拼接
     *
     * @param url
     * @param paramsMap
     *
     * @return
     */
    private String appendParams(String url, @NonNull Map<String, Object> paramsMap) {
        StringBuilder sb = new StringBuilder();
        sb.append(url).append("?");
        for (String key : paramsMap.keySet()) {
            sb.append(key).append("=").append(Configure.encode ? URLEncoder.encode(String.valueOf(paramsMap.get(key))) : paramsMap.get(key)).append("&");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
