package com.hyc.libs.base.mvp;

import com.hyc.libs.http.HttpHelper;

/**
 * @author zhxumao
 *         Created on 2018/1/2 0002 14:52.
 */

public abstract class BasePresenter<V, M> {

    public V view;
    public M model;

    public BasePresenter(V view, M model) {
        attachView(view, model);
    }

    public void attachView(V view, M model) {
        this.view = view;
        this.model = model;
    }

    public void detachView() {
        HttpHelper.getInstance().cancel(model);
        this.view = null;
    }

}
