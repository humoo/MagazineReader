package com.hyc.libs.widget.dialog;

import android.view.View;

/**
 * @author zhxumao
 *         Created on 2018/1/15 0015 15:24.
 */

public interface HDialogStyle {

    /**
     * 設置view展示所需要的參數
     *
     * @param loadingDialog
     */
    void setting(HDialog loadingDialog);

    /**
     * 設置自定義的View layout id
     *
     * @param loadingDialog
     * @return
     */
    View getContentView(HDialog loadingDialog);

    /**
     * 添加動作監聽
     *
     * @param loadingDialog
     * @param targetId
     * @param listener
     * @param flag 区分动作
     */
    void addAction(HDialog loadingDialog, int targetId, HDialog.OnHDialogClickListener listener, int flag);
}
