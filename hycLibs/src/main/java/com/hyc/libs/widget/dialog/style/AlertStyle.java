package com.hyc.libs.widget.dialog.style;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyc.libs.R;
import com.hyc.libs.widget.dialog.HDialog;
import com.hyc.libs.widget.dialog.HDialogStyle;

/**
 * 常見的dialog樣式 - 帶確定提示/帶確定取消提示
 *
 * @author zhxumao
 *         Created on 2018/1/15 0015 17:11.
 */

public class AlertStyle implements HDialogStyle, View.OnClickListener {

    public enum TYPE {
        WITH_CONFIRM, WITH_CONFIRM_CANCEL
    }

    private View view;
    private TextView noteText, tvConfirm;
    private LinearLayout llCancel;
    private TYPE type;
    private HDialog hDialog;
    private HDialog.OnHDialogClickListener confirmListener, cancelListener;

    public AlertStyle(TYPE type) {
        this.type = type;
    }

    @Override
    public void setting(HDialog loadingDialog) {
    }

    @Override
    public View getContentView(HDialog dialog) {
        this.hDialog = dialog;
        if (type == TYPE.WITH_CONFIRM) {
            view = View.inflate(hDialog.getContext(), R.layout.dialog_alert_one, null);
            llCancel = view.findViewById(R.id.llCancel);
            llCancel.setVisibility(View.GONE);
        } else {
            view = View.inflate(hDialog.getContext(), R.layout.dialog_alert_one, null);
            llCancel = view.findViewById(R.id.llCancel);
            llCancel.setOnClickListener(this);
        }
        noteText = view.findViewById(R.id.tvNote);
        tvConfirm = view.findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(this);
        return view;
    }

    @Override
    public void addAction(final HDialog loadingDialog, int targetId, final HDialog.OnHDialogClickListener listener,int flag) {

    }

    public void setNoteText(String text) {
        noteText.setText(text);
    }

    public void addConfirmListener(final HDialog.OnHDialogClickListener listener) {
        confirmListener = listener;
    }

    public void addCancelListener(final HDialog.OnHDialogClickListener listener) {
        cancelListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (hDialog == null || !hDialog.isShowing()) {
            return;
        }

        hDialog.dismiss();
        int id = v.getId();
        if (id == R.id.tvConfirm) {
            if (confirmListener != null) {
                confirmListener.onHDialogClick(null,0);
            }
        } else {
            if (cancelListener != null) {
                cancelListener.onHDialogClick(null,0);
            }
        }
    }

}
