package com.hyc.libs.http.callback;

import com.lzy.okgo.model.Response;

/**
 * @author zhxumao
 *         Created on 2018/1/11 0011 14:19.
 */

public interface HttpCallBackListener<String> {

    void onSuccess(Response<String> response, int requestCode);

    void onError(Response<String> response, int requestCode);

}
