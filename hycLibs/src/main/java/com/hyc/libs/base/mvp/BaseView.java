package com.hyc.libs.base.mvp;

/**
 * @author zhxumao
 *         Created on 2018/1/2 0002 14:53.
 */

public interface BaseView<B> {

    void onSuccess(B bean);

    void onError(String message);

}
