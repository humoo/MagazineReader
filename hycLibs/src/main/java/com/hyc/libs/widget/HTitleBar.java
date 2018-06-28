package com.hyc.libs.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyc.libs.R;
import com.hyc.libs.utils.scalable.ScalableUtils;


/**
 * 自定义状态栏
 *
 * @author zhxumao
 *         Created on 2018/1/24 0024 15:35.
 */

public class HTitleBar extends FrameLayout {

    public static final int STYLE_WHITE = 0;
    public static final int STYLE_COLOR = 1;

    /**
     * 左侧View
     */
    private ImageView mLeftView;
    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 右侧View
     */
    private ImageView mRightView;
    /**
     * 右侧TextView
     */
    private TextView mRightTextView;

    /**
     * 状态栏占位 view
     */
    private View fake_status_bar;

    /**
     * 主体
     */
    private RelativeLayout rlTitleBar;

    /**
     * 整条
     */
    private LinearLayout rootTitleBar;

    public HTitleBar(Context context) {
        super(context);
        init(context);
    }

    public HTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        inflate(context, R.layout.w_title_bar_white, this);

        mTxtMiddleTitle = findViewById(R.id.title_center);
        mLeftView = findViewById(R.id.ivBack);
        mRightView = findViewById(R.id.ivAction);
        mRightTextView = findViewById(R.id.tvRightText);
        fake_status_bar = findViewById(R.id.fake_status_bar);
        rlTitleBar = findViewById(R.id.rlTitleBar);
        rootTitleBar = findViewById(R.id.rootTitleBar);
        ViewGroup.LayoutParams lp = fake_status_bar.getLayoutParams();

        lp.height = ScalableUtils.getStatusHeight19(context);
        fake_status_bar.setLayoutParams(lp);
    }

    /**
     * 设置左侧view图标
     *
     * @param res
     */
    public void setLeftViewImage(int res) {
        mLeftView.setImageResource(res);
    }

    /**
     * 设置左侧view点击监听
     *
     * @param listener
     */
    public void setLeftViewClickListener(OnClickListener listener) {
        mLeftView.setOnClickListener(listener);
    }

    /**
     * 隐藏左侧view
     */
    public void hideLeftView() {
        mLeftView.setVisibility(View.GONE);
    }

    /**
     * 显示左侧view
     */
    public void showLeftView() {
        mLeftView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置中间title的内容
     */
    public void setMainTitle(String text) {
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }

    /**
     * 设置右侧View图标
     */
    public void setRightViewImage(int res) {
        mRightView.setImageResource(res);
    }

    /**
     * 设置右侧View点击事件
     */
    public void setRightViewClickListener(OnClickListener onClickListener) {
        mRightView.setOnClickListener(onClickListener);
    }

    /**
     * 隐藏右侧view
     */
    public void hideRightView() {
        mRightView.setVisibility(View.GONE);
    }

    /**
     * 显示右侧view
     */
    public void showRightView() {
        mRightView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧TextView点击事件
     */
    public void setRightTextViewClickListener(OnClickListener onClickListener) {
        mRightTextView.setOnClickListener(onClickListener);
    }

    /**
     * 设置右侧Textview
     */
    public void setRightTextView(String text) {
        mRightTextView.setText(text);
    }

    /**
     * 隐藏右侧Textview
     */
    public void hideRightTextView() {
        mRightTextView.setVisibility(View.GONE);
    }

    /**
     * 显示右侧TextView
     */
    public void showRightTextView() {
        mRightTextView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题栏背景
     *
     * @param resId
     */
    public void setTitleBarBackground(int resId) {
        fake_status_bar.setBackgroundColor(Color.TRANSPARENT);
        rlTitleBar.setBackgroundColor(Color.TRANSPARENT);
        rootTitleBar.setBackground(getResources().getDrawable(resId));
    }
}
