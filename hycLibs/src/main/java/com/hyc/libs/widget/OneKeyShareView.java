package com.hyc.libs.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyc.libs.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 自定义分享面板
 * Created by zhxumao on 2018/3/13.
 */

public class OneKeyShareView {

    private int tvTextColor = Color.parseColor("#666666");
    private int cancelTextColor = Color.parseColor("#333333");

    private int row = 1;
    private int column = 3;
    private int paddingLeft = 30, paddingRight = 30, paddingTop = 20, paddingBottom = 5, cancelMarginTop = 0, cancelMarginBottom = 10;
    private boolean cancel = true;
    private List<PanelView> textViews;
    private OnShareSelectedListener listener;
    private int drawablePadding = 5;
    private String cancelText = "取 消";
    private int rawPadding = 15;

    public OneKeyShareView() {
        textViews = new ArrayList<>();
    }

    /**
     * 添加分享项
     *
     * @param drawableId
     * @param text
     */
    public OneKeyShareView addView(int drawableId, String text) {
        PanelView panelView = new PanelView();
        panelView.setDrawableId(drawableId);
        panelView.setText(text);
        textViews.add(panelView);
        return this;
    }

    /**
     * 添加分享项
     *
     * @param list
     */
    public OneKeyShareView addViews(List<PanelView> list) {
        textViews.addAll(list);
        return this;
    }

//    /**
//     * 设置面板图标行数
//     */
//    public OneKeyShareView setPanelRow(int row) {
//        this.row = row;
//        return this;
//    }

    /**
     * 设置行间距
     *
     * @param rawPadding
     */
    public OneKeyShareView setRawPadding(int rawPadding) {
        this.rawPadding = rawPadding;
        return this;
    }

    /**
     * 设置面板图标列数
     */
    public OneKeyShareView setPanelColumn(int column) {
        this.column = column;
        return this;
    }

    /**
     * 设置取消按钮 - 默认带取消按钮
     */
    public OneKeyShareView setCancel(boolean b) {
        this.cancel = b;
        return this;
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public OneKeyShareView setShareSelectedListener(OnShareSelectedListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 设置面板内边距
     *
     * @param l
     * @param t
     * @param r
     * @param b
     * @return
     */
    public OneKeyShareView setPadding(int l, int t, int r, int b) {
        this.paddingLeft = l;
        this.paddingTop = t;
        this.paddingRight = r;
        this.paddingBottom = b;
        return this;
    }

    /**
     * 设置按钮外边距
     *
     * @param t
     * @param b
     * @return
     */
    public OneKeyShareView setCancelMargin(int t, int b) {
        this.cancelMarginBottom = b;
        this.cancelMarginTop = t;
        return this;
    }

    /**
     * 设置按钮外边距
     *
     * @param text
     * @return
     */
    public OneKeyShareView setCancelText(String text) {
        this.cancelText = text;
        return this;
    }

    public OneKeyShareView setTvTextColor(int tvTextColor) {
        this.tvTextColor = tvTextColor;
        return this;
    }

    public OneKeyShareView setCancelTextColor(int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
        return this;
    }

    public OneKeyShareView setDrawablePadding(int drawablePadding) {
        this.drawablePadding = drawablePadding;
        return this;
    }

    /**
     * 实际创建在这里
     */
    public void create(Context context, View parent) {

        //根据图标数量及列数 计算行数
        if (row * column < textViews.size()) {
            row = textViews.size() % column == 0 ? textViews.size() / column : textViews.size() / column + 1;
        }

        SharePanelView onkeyShareView = new SharePanelView(context);
        onkeyShareView.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    public interface OnShareSelectedListener {
        void onShared(int i);
    }

    public static class PanelView {

        private int drawableId;
        private String text;

        public int getDrawableId() {
            return drawableId;
        }

        public void setDrawableId(int drawableId) {
            this.drawableId = drawableId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private class SharePanelView extends PopupWindow implements PopupWindow.OnDismissListener {

        private View rootView;
        private Context mContext;

        public SharePanelView(Context context) {
            super(context);
            mContext = context;
            rootView = createView();

            setFocusable(true);
            setOutsideTouchable(true);
            setAnimationStyle(R.style.BottomSelectAnimationShow);

            setWidth(MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            // 菜单背景色。加了一点透明度
            backgroundAlpha(0.7f);
            setBackgroundDrawable(new BitmapDrawable());

//        //sdk > 21 解决 标题栏没有办法遮罩的问题
//        setClippingEnabled(false);

            setContentView(rootView);
            findComponent();

            setOnDismissListener(this);
        }

        /**
         * 动态创建view
         */
        private View createView() {

            //最外围的布局
            LinearLayout ll_0 = new LinearLayout(mContext);
            LinearLayout.LayoutParams lp_0 = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            ll_0.setOrientation(LinearLayout.VERTICAL);
            ll_0.setPadding(getTransDp(paddingLeft), getTransDp(paddingTop), getTransDp(paddingRight), getTransDp(paddingBottom));
            ll_0.setBackgroundColor(Color.WHITE);
            ll_0.setLayoutParams(lp_0);

            //根据行数生成linearLayout
            int poi = 0;//被创建view的数量
            for (int i = 1; i <= row; i++) {
                LinearLayout ll_i = new LinearLayout(mContext);
                LinearLayout.LayoutParams lp_i = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                ll_i.setPadding(getTransDp(5), getTransDp(rawPadding), getTransDp(5), getTransDp(5));
                ll_i.setOrientation(LinearLayout.HORIZONTAL);
                ll_i.setLayoutParams(lp_i);

                //根据行数和图标数量判断本行需要加载几个图标
                for (int j = 0; j < column; j++) {
                    //判断控制view是否已经创建完毕
                    if (poi < textViews.size()) {
                        TextView textView = new TextView(mContext);
                        textView.setText(textViews.get(poi).getText());
                        textView.setClickable(true);
                        Drawable image = mContext.getResources().getDrawable(textViews.get(poi).getDrawableId());
                        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
                        textView.setCompoundDrawables(null, image, null, null);
                        textView.setCompoundDrawablePadding(getTransDp(drawablePadding));
                        textView.setGravity(Gravity.CENTER);
                        textView.setTextColor(tvTextColor);
                        final int finalPoi = poi;
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null) {
                                    listener.onShared(finalPoi);
                                }
                                dismiss();
                            }
                        });
                        LinearLayout.LayoutParams txLp = new LinearLayout.LayoutParams(0, WRAP_CONTENT);
                        txLp.weight = 1;
                        textView.setLayoutParams(txLp);
                        ll_i.addView(textView);
                        poi++;
                    } else {
                        TextView textView = new TextView(mContext);
                        textView.setGravity(Gravity.CENTER);
                        textView.setClickable(true);
                        LinearLayout.LayoutParams txLp = new LinearLayout.LayoutParams(0, WRAP_CONTENT);
                        txLp.weight = 1;
                        textView.setLayoutParams(txLp);
                        ll_i.addView(textView);
                    }
                }
                ll_0.addView(ll_i);
            }

            //添加取消按钮
            if (cancel) {
                TextView textView = new TextView(mContext);
                LinearLayout.LayoutParams txLp = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                txLp.setMargins(0, getTransDp(cancelMarginTop), 0, getTransDp(cancelMarginBottom));
                textView.setLayoutParams(txLp);
                textView.setText(cancelText);
                textView.setTextColor(cancelTextColor);
                textView.setTextSize(18);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(getTransDp(10), getTransDp(10), getTransDp(10), getTransDp(10));
                textView.setClickable(true);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                ll_0.addView(textView);
            }
            return ll_0;
        }

        private void findComponent() {

        }

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

        private void backgroundAlpha(float bgAlpha) {
            WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
            lp.alpha = bgAlpha; // 0.0-1.0
            ((Activity) mContext).getWindow().setAttributes(lp);
        }

        private int getTransDp(int dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        }
    }

}
