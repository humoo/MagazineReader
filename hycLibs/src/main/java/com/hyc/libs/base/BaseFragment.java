package com.hyc.libs.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyc.libs.widget.dialog.HDialog;
import com.hyc.libs.widget.dialog.HDialogStyle;
import com.hyc.libs.widget.dialog.style.WXLoadingStyle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zhxumao
 *         Created on 2017/12/27 0027 16:53.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseFragmentActivity context;
    protected View rootView;
    private HDialog dialog;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseFragmentActivity) getActivity();
        setLeakWatcher();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(inflater, getViewId());
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 调用该办法可避免重复加载UI
     */
    public View setContentView(LayoutInflater inflater, int resId) {
        if (rootView == null) {
            rootView = inflater.inflate(resId, null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    protected void setLeakWatcher() {
//        HApplication.getRefWatcher(context).watch(this);
    }

    /**
     * 防止activity回调事件冲突，导致fragment的activityResult接收不到
     * 直接使用startActivityForResult
     * 而不是activity.startActivityForResult
     *
     * @param bundle
     * @param requestCode
     * @param target
     */
    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(context, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void finish() {
        context.finish();
    }

    public void showLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        if (getActivity() == null) {
            return;
        }
        dialog = new HDialog(getActivity());
        HDialogStyle style = new WXLoadingStyle();
        dialog.setStyle(style);
        dialog.show();
    }

    public void dismissLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected abstract int getViewId();

    protected abstract void init();

}
