package com.hyc.libs.widget.dialog.style;

import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.hyc.libs.R;
import com.hyc.libs.widget.dialog.HDialog;
import com.hyc.libs.widget.dialog.HDialogStyle;


/**
 * 微信加載loading動畫效果
 *
 * @author zhxumao
 *         Created on 2018/1/15 0015 15:42.
 */

public class WXLoadingStyle implements HDialogStyle {

    private View view;

    @Override
    public void setting(HDialog loadingDialog) {

        Window window = loadingDialog.getWindow();
        if (window != null) {
            //設置背景樣式，但需要在代碼中設置組件寬度
            loadingDialog.getWindow().setBackgroundDrawable(loadingDialog.getContext().getResources().getDrawable(R.drawable.wx_loading_bg));
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, loadingDialog.getContext().getResources().getDisplayMetrics());
            lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, loadingDialog.getContext().getResources().getDisplayMetrics());
            window.setAttributes(lp);
        }
    }

    @Override
    public View getContentView(HDialog loadingDialog) {
        view = View.inflate(loadingDialog.getContext(), R.layout.simple_alert, null);
        ImageView loadImage = view.findViewById(R.id.loadImage);
        RotateAnimation rotateAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(4000);
        rotateAnim.setRepeatCount(Animation.INFINITE);//动画的重复次数
        rotateAnim.setInterpolator(new LinearInterpolator());
        loadImage.setAnimation(rotateAnim);
        return view;
    }

    @Override
    public void addAction(HDialog loadingDialog, int targetId, final HDialog.OnHDialogClickListener listener,int flag) {

    }
}
