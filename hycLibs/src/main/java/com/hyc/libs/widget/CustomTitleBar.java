package com.hyc.libs.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyc.libs.R;

/**
 * 简单的titleBar，左侧选项/标题/右侧选项
 *
 * @author zhxumao
 *         Created on 2018/1/2 0002 18:22.
 */

public class CustomTitleBar extends FrameLayout {

    private RelativeLayout mRootTitleBar;
    /**
     * 左侧Back
     */
    private ImageView mBackButton;
    /**
     * 左侧Title
     */
    private TextView mTxtLeftTitle;
    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 右侧Title
     */
    private TextView mTxtRightTitle;


    public CustomTitleBar(Context context) {
        super(context);
        init(context);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        inflate(context, R.layout.custom_titie_bar, this);

        mRootTitleBar = findViewById(R.id.rootTitleBar);
        mBackButton = findViewById(R.id.image_back);
        mTxtLeftTitle = findViewById(R.id.txt_left_title);
        mTxtMiddleTitle = findViewById(R.id.txt_main_title);
        mTxtRightTitle = findViewById(R.id.txt_right_title);

        mBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context != null) {
                    ((Activity) context).finish();
                }
            }
        });
    }

    /**
     * 设置左侧返回按钮图标
     *
     * @param color
     */
    public void setTitleBarColor(int color) {
        mRootTitleBar.setBackgroundColor(color);
    }

    /**
     * 设置左侧返回按钮图标
     *
     * @param res
     */
    public void setBackButtonDrawable(int res) {
        mBackButton.setImageDrawable(getResources().getDrawable(res));
        mBackButton.setVisibility(View.VISIBLE);
    }

    /**
     * 设置返回按钮点击监听
     *
     * @param listener
     */
    public void setBackButtonClickListener(OnClickListener listener) {
        mBackButton.setOnClickListener(listener);
    }

    /**
     * 隐藏返回按钮
     */
    public void hideBackButton() {
        mBackButton.setVisibility(View.GONE);
    }

    /**
     * 设置中间title的内容
     */
    public void setMainTitle(String text) {
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }

    /**
     * 设置中间title的内容文字的颜色
     */
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    /**
     * 设置title左边文字大小
     *
     * @param size
     */
    public void setLeftTitleSize(float size) {
        mTxtLeftTitle.setTextSize(size);
    }

    /**
     * 设置title左边文字
     */
    public void setLeftTitleText(String text) {
        mTxtLeftTitle.setVisibility(View.VISIBLE);
        mTxtLeftTitle.setText(text);
    }

    /**
     * 设置title左边文字颜色
     */
    public void setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
    }

    /**
     * 设置title左边图标
     */
    public void setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mTxtLeftTitle.setCompoundDrawables(dwLeft, null, null, null);
    }

    /**
     * 设置title左边点击事件
     */
    public void setLeftTitleClickListener(OnClickListener onClickListener) {
        mTxtLeftTitle.setOnClickListener(onClickListener);
    }

    /**
     * 设置title右边文字
     */
    public void setRightTitleText(String text) {
        mTxtRightTitle.setVisibility(View.VISIBLE);
        mTxtRightTitle.setText(text);
    }

    /**
     * 设置title右边文字颜色
     */
    public void setRightTitleColor(int color) {
        mTxtRightTitle.setTextColor(color);
    }

    /**
     * 设置title右边图标
     */
    public void setRightTitleDrawable(int res) {
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mTxtRightTitle.setCompoundDrawables(null, null, dwRight, null);
    }

    /**
     * 设置title右边点击事件
     */
    public void setRightTitleClickListener(OnClickListener onClickListener) {
        mTxtRightTitle.setOnClickListener(onClickListener);
    }

}
