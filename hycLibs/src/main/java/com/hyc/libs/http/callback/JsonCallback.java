package com.hyc.libs.http.callback;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * 根据返回的json数据解析为实体类
 *
 * @author zhxumao
 *         Created on 2017/12/28 0028 17:10.
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    public JsonCallback() {
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        data = gson.fromJson(jsonReader, type);

        return data;
    }
}
