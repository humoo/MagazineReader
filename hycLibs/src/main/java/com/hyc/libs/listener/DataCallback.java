package com.hyc.libs.listener;

/**
 * Created by zhxumao on 2018/4/20.
 */

public interface DataCallback<B> {

    void onDealSuccess(B bean);

    /**
     * 默认后台返回的提示信息
     *
     * @param message
     */
    void onDealError(int code, String message);
}
