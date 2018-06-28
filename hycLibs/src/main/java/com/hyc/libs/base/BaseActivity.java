package com.hyc.libs.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyc.libs.utils.ActivityManager;
import com.hyc.libs.utils.StatusBarHelper;
import com.hyc.libs.widget.CustomTitleBar;
import com.hyc.libs.widget.dialog.HDialog;
import com.hyc.libs.widget.dialog.HDialogStyle;
import com.hyc.libs.widget.dialog.style.WXLoadingStyle;

import butterknife.ButterKnife;

/**
 * @author zhxumao
 *         Created on 2017/12/27 0027 16:50.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public CustomTitleBar superTitleBar;

    public LinearLayout parentLinearLayout;

    private HDialog dialog;

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
     */
    public abstract void initialize();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        featureSetting();
        ActivityManager.getInstance().addActivity(this);
        initSystemBarTint();
        initParentView();
        initSuperTitleBar();
        setContentView(setLayoutResource());
        ButterKnife.bind(this);
        initialize();
        setLeakWatcher();
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
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
     * 子类可以在未渲染页面前做一些操作
     */
    protected void featureSetting() {
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

    public void showLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new HDialog(this);
        HDialogStyle style = new WXLoadingStyle();
        dialog.setStyle(style);
        dialog.show();
    }

    public void dismissLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void displayImage(String url, ImageView imageView) {


    }
}
