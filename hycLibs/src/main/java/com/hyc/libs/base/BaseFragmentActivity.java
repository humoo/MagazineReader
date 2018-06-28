package com.hyc.libs.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyc.libs.utils.ActivityManager;
import com.hyc.libs.utils.StatusBarHelper;
import com.hyc.libs.widget.CustomTitleBar;

import butterknife.ButterKnife;

/**
 * @author zhxumao
 *         Created on 2018/1/5 0005 09:45.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {

    public CustomTitleBar superTitleBar;

    public LinearLayout parentLinearLayout;

    /**
     * 设置页面布局（titleBar可以不要，baseActivity默认提供）
     *
     * @return
     */
    public abstract int setLayoutResource();

    /**
     * 设置主标题
     *
     * @return
     */
    public abstract String setMainTitle();

    /**
     * 页面中的操作在这里编写
     *
     * @return
     * @param savedInstanceState
     */
    public abstract void initialize(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        initSystemBarTint();
        initParentView();
        initSuperTitleBar();
        setContentView(setLayoutResource());
        ButterKnife.bind(this);
        initialize(savedInstanceState);
        setLeakWatcher();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        parentLinearLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        parentLinearLayout.addView(view, params);
    }

    protected void setLeakWatcher() {
//        HApplication.getRefWatcher(this).watch(this);
    }

    /**
     * 初始化默认的titleBar
     */
    protected void initSuperTitleBar() {
        if (!unUseSuperTitleBar()) {
            superTitleBar = new CustomTitleBar(this);
            parentLinearLayout.addView(superTitleBar);
            if (!TextUtils.isEmpty(setMainTitle())) {
                superTitleBar.setMainTitle(setMainTitle());
            }
        }
    }

    /**
     * 初始化基础布局
     */
    private void initParentView() {
        ViewGroup group = (ViewGroup) findViewById(android.R.id.content);
        group.removeAllViews(); //首先先移除在根布局上的组件
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        group.addView(parentLinearLayout);
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 子类可以重写决定是否使用基础titleBar
     */
    protected boolean unUseSuperTitleBar() {
        return false;
    }

    /**
     * 设置状态栏
     */
    protected void initSystemBarTint() {
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            StatusBarHelper.immersiveStatusBar(this, 0f);//保留虚拟按键占用空间
//            StatusBarHelper.tintStatusBar(this, Color.RED);//保留虚拟按键占用空间
//            StatusBarUtil.transparencyBar(this);
//            getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private ProgressDialog dialog;

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void displayImage(String url, ImageView imageView) {


    }

}
