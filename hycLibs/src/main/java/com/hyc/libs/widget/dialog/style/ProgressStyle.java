package com.hyc.libs.widget.dialog.style;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hyc.libs.R;
import com.hyc.libs.utils.rxtool.RxDeviceTool;
import com.hyc.libs.widget.dialog.HDialog;
import com.hyc.libs.widget.dialog.HDialogStyle;

/**
 * 圓形進度條旋轉樣式
 *
 * @author zhxumao
 *         Created on 2018/1/15 0015 15:26.
 */

public class ProgressStyle implements HDialogStyle {

    private View view;

    @Override
    public void setting(HDialog loadingDialog) {
        Window window = loadingDialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        mLayoutParams.width = (int) (RxDeviceTool.getScreenWidth(loadingDialog.getContext()) - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, loadingDialog.getContext().getResources().getDisplayMetrics()));
        mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public View getContentView(HDialog loadingDialog) {
        view = View.inflate(loadingDialog.getContext(), R.layout.progress_bar, null);
        return view;
    }

    @Override
    public void addAction(HDialog loadingDialog, int targetId, final HDialog.OnHDialogClickListener listener,int flag) {
    }

}
