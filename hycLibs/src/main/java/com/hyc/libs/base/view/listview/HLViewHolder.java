package com.hyc.libs.base.view.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ListView 基础布局
 * Created by zhxumao on 2017/12/4 18:28.
 */

public abstract class HLViewHolder<T> {

    public Context mContext;
    public HListener mHListener;
    private int layoutRes;
    private View containerView;

    public HLViewHolder(Context context, int layoutRes, HListener listener) {
        this.layoutRes = layoutRes;
        this.mContext = context;
        this.mHListener = listener;
    }

    public View getRootView(ViewGroup root) {
        View rootView = LayoutInflater.from(mContext).inflate(layoutRes, root, false);
        containerView = rootView;
        bindView(rootView);
        return rootView;
    }

    public Context getContext() {
        return mContext;
    }

    public HListener getHListener() {
        return mHListener;
    }

    /**
     * 绑定数据入口
     */
    public abstract void bindData(T t, int position, int selectedP);

    public abstract void bindView(View rootView);

}
