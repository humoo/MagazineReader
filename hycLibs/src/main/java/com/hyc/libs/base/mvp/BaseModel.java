package com.hyc.libs.base.mvp;

/**
 * Created by zhxumao on 2018/4/20.
 */

public class BaseModel<V> {

    private V view;

    public BaseModel(V view) {
        this.view = view;
    }

    public boolean isViewNull() {
        return view == null;
    }

}
