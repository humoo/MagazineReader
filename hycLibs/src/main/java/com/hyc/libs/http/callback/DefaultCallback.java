package com.hyc.libs.http.callback;

import com.hyc.libs.log.Debug;
import com.lzy.okgo.callback.AbsCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;

/**
 * 返回JSONObject数据类型 - 解决json解析编码问题
 *
 * @author zhxumao
 *         Created on 2017/12/29 0029 10:21.
 */

public abstract class DefaultCallback extends AbsCallback<JSONObject> {

    @Override
    public JSONObject convertResponse(okhttp3.Response response) throws Throwable {
        //解决json中文unicode编码问题
        ResponseBody body = response.body();
        if (body == null) return null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(body.string());
        } catch (JSONException e) {
            Debug.w("json parse error = " + e.toString());
        }
        return jsonObject;
    }

}
