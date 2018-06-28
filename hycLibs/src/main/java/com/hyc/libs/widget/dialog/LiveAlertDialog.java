package com.hyc.libs.widget.dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.hyc.libs.R;


/**
 * 提示框
 *
 * @author zhxumao
 *         Created on 2018/1/10 0010 16:29.
 */
public class LiveAlertDialog extends AlertDialog {
    private Context mContext;

    public LiveAlertDialog(Context context) {
//        super(context);
        //設置背景透明,使用此種方式，需要在xml中預設置好組件内間距等參數
        super(context,R.style.NoBackGroundDialog);
        mContext = context;
    }

    public LiveAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_alert);
//        Window window = getWindow();
//        if (window != null) {
            //設置背景樣式，但需要在代碼中設置組件寬度
//            getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.wx_loading_bg));
            //获得window窗口的属性
//            WindowManager.LayoutParams lp = window.getAttributes();
//            //设置窗口宽度为充满全屏
//            lp.width = 300;
//            //设置窗口高度为包裹内容
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            //将设置好的属性set回去
//            window.setAttributes(lp);
//        }
    }

    /**
     * 设置文本内容,并且显示弹出框
     *
     * @param msg
     */
    public void show(String msg) {
        super.show();
    }


}
