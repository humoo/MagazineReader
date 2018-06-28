package com.hyc.libs.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hyc.libs.log.Debug;


/**
 * 下拉回弹View
 * <p>
 * 未完成
 *
 * @author zhxumao
 *         Created on 2017/12/29 0029 16:14.
 */

public class KickBackScrollView extends NestedScrollView implements View.OnTouchListener {

    private Activity activity;

    private int defaultW, defaultH;

    // 记录首次按下位置
    private float mFirstPosition = 0;

    // 是否正在放大
    private Boolean mScaling = false;

    private DisplayMetrics metric;

    private ViewGroup mHead;

    public KickBackScrollView(Context context) {
        super(context);
        init(context);
    }

    public KickBackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KickBackScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void addScaleView(Activity activity, ViewGroup headView) {
        this.activity = activity;
        this.mHead = headView;

        //获取屏幕宽高
        metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        //获取弹性View的默认宽高
        ViewGroup.LayoutParams lp = headView.getLayoutParams();
        defaultW = lp.width;//默认应为屏幕等宽
        defaultH = lp.height;

        setOnTouchListener(this);
    }

    private void init(Context context) {


    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Debug.d("滑动:" + motionEvent.getY() + "   ---   " + mFirstPosition);

        ViewGroup.LayoutParams lp = mHead.getLayoutParams();
        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_UP:
                mScaling = false;
                replyImage();
                break;
            case MotionEvent.ACTION_MOVE:
                Debug.d("getScrollY = " + getScrollY());//列表位移距离
                Debug.d("getY = " + motionEvent.getY());//手指落在当前屏幕的Y高

                if (!mScaling) {
                    if (getScrollY() == 0) {//记录页面手指第一次按下的位置
                        mFirstPosition = motionEvent.getY();
                    } else {
                        break;
                    }
                }

                if (motionEvent.getY() - mFirstPosition < 0) {
                    break;
                }

                int distance = (int) ((motionEvent.getY() - mFirstPosition));
                if (distance < 0) {
                    break;
                }

                mScaling = true;
                lp.height = defaultH + distance;
                lp.width = defaultW;
                mHead.setLayoutParams(lp);
                Debug.d("滑动:" + motionEvent.getY() + "   ---   " + mFirstPosition);//手指偏移高度

                Debug.d("滑动 y = :" + (motionEvent.getY() - mFirstPosition));//手指偏移高度

                return true;

            default:
                break;

        }

        return false;
    }

    private void replyImage() {
        final ViewGroup.LayoutParams lp = mHead.getLayoutParams();

        Debug.d("mHead.getLayoutParams().width = " + mHead.getLayoutParams().width);
        Debug.d("mHead.getWidth = " + mHead.getWidth());

        Debug.d("mHead.getLayoutParams().height = " + mHead.getLayoutParams().height);
        Debug.d("mHead.getHeight = " + mHead.getHeight());

        final float w = mHead.getLayoutParams().width;
        final float h = mHead.getLayoutParams().height;

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0.0f, 1.0f).setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                float cVal = (float) animator.getAnimatedValue();
                lp.width = (int) (w - (w - defaultW) * cVal);
                lp.height = (int) (h - (h - defaultH) * cVal);
                mHead.setLayoutParams(lp);
            }
        });
    }
}
