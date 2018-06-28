package com.hyc.libs.listener;


import com.hyc.libs.utils.rxtool.RxToast;

/**
 * 默认提示错误的实现类
 * Created by zhxumao on 2018/5/14.
 */

public abstract class DataCallBackImpl<B> implements DataCallback<B> {

    @Override
    public void onDealError(int code,String message) {
        RxToast.normal(message);
    }

}
