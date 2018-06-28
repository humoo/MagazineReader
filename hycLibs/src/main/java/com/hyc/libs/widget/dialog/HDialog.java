package com.hyc.libs.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.hyc.libs.R;

/**
 * 自定義樣式的Dialog,支持傳id 點擊事件
 *
 * @author zhxumao
 *         Created on 2018/1/10 0010 16:29.
 */

public class HDialog extends Dialog {

    private HDialogStyle loadingStyle;

    public HDialog(Context context) {
        super(context);
    }

    /**
     * 取消背景遮罩層
     *
     * @param context
     * @param style
     */
    public HDialog(Activity context, int style) {
        super(context, R.style.NoBackGroundDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStyle(HDialogStyle style) {
        this.loadingStyle = style;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(style.getContentView(this));
        style.setting(this);
    }

    /**
     *
     * @param targetId
     * @param listener
     * @param flag 原样返回，用来区分动作
     */
    public void addAction(int targetId, OnHDialogClickListener listener,int flag) {
        if (loadingStyle != null) {
            loadingStyle.addAction(this, targetId, listener,flag);
        }
    }


    public interface OnHDialogClickListener {
        void onHDialogClick(Object obj, int flag);
    }
}
