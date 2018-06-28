package com.hyc.libs.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyc.libs.base.BaseFragment;
import com.hyc.libs.widget.dialog.HDialog;
import com.hyc.libs.widget.dialog.HDialogStyle;
import com.hyc.libs.widget.dialog.style.WXLoadingStyle;

/**
 * @author zhxumao
 *         Created on 2018/1/24 0024 11:12.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P presenter;

    protected abstract P initPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = initPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    private HDialog myDialog;

    @Override
    public void showLoadingDialog() {
        if (myDialog != null && myDialog.isShowing()) {
            return;
        }
        myDialog = new HDialog(getActivity());
        HDialogStyle style = new WXLoadingStyle();
        myDialog.setStyle(style);
        myDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }

}
