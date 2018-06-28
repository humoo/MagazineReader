package com.hyc.libs.widget;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 适配android 7.0 pop 弹出位置错误bug
 * Created by zhxumao on 2017/11/16 17:19.
 */

public class Fix7BasePop extends PopupWindow {

    public Fix7BasePop(Context context) {
        super(context);
    }

    @Override
    public void showAsDropDown(View anchorView, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchorView.getLocationInWindow(a);
            showAtLocation(anchorView, Gravity.NO_GRAVITY, xoff, a[1] + anchorView.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchorView, xoff, yoff);
        }
    }

    @Override
    public void showAsDropDown(View anchorView) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchorView.getLocationInWindow(a);
            showAtLocation(anchorView, Gravity.NO_GRAVITY, 0, a[1] + anchorView.getHeight());
        } else {
            super.showAsDropDown(anchorView);
        }
    }
}
