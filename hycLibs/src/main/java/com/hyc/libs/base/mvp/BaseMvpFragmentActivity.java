package com.hyc.libs.base.mvp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.hyc.libs.base.BaseFragmentActivity;
import com.hyc.libs.utils.StatusBarHelper;
import com.hyc.libs.widget.HTitleBar;

/**
 * @author zhxumao
 *         Created on 2018/1/24 0024 17:29.
 */

public abstract class BaseMvpFragmentActivity<P extends BasePresenter> extends BaseFragmentActivity {

    protected P presenter;

    protected abstract P initPresenter();

    public HTitleBar hTitleBar;

    private View decorView;
    private int screenWidth;//屏宽


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();

        decorView = getWindow().getDecorView();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    protected boolean unUseSuperTitleBar() {
        return true;
    }

    protected boolean unUseAllTitleBar() {
        return false;
    }

    /**
     * 右滑开关
     *
     * @return
     */
    protected boolean useRightSliding() {
        return false;
    }

    @Override
    protected void initSystemBarTint() {
//        StatusBarUtil.transparencyBar(this);
        StatusBarHelper.immersiveStatusBar(this, 0f);//保留虚拟按键占用空间
    }

    /**
     * 当开关被开启，优先使用该设置,默认为白色背景
     *
     * @return
     */
    protected boolean useColorBgTitleBar() {
        return false;
    }

    @Override
    protected void initSuperTitleBar() {
        if (!unUseAllTitleBar()) {
            hTitleBar = new HTitleBar(this);
            parentLinearLayout.addView(hTitleBar);

            //默认配置
            hTitleBar.hideRightView();
            hTitleBar.hideRightTextView();
            hTitleBar.setLeftViewClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            if (!TextUtils.isEmpty(setMainTitle())) {
                hTitleBar.setMainTitle(setMainTitle());
            }
        }
    }

    /**
     * 右滑退出
     */
    float startX, startY, endX, endY, distanceX, distanceY;
    boolean canMove = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!canMove) {
            return false;
        }
        if (useRightSliding()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (startX > 20) {
                        break;
                    }
                    endX = event.getX();
                    endY = event.getY();
                    distanceX = endX - startX;
                    distanceY = Math.abs(endY - startY);
                    //1.判断手势右滑  2.横向滑动的距离要大于竖向滑动的距离
                    if (endX - startX > 0 && distanceY < distanceX) {
                        decorView.setX(distanceX);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (startX > 20) {
                        break;
                    }
                    endX = event.getX();
                    distanceX = endX - startX;
                    endY = event.getY();
                    distanceY = Math.abs(endY - startY);
                    //1.判断手势右滑  2.横向滑动的距离要大于竖向滑动的距离 3.横向滑动距离大于屏幕三分之一才能finish
                    if (endX - startX > 0 && distanceY < distanceX && distanceX > screenWidth / 3) {
                        moveOn(distanceX);
                    }
                    //1.判断手势右滑  2.横向滑动的距离要大于竖向滑动的距离 但是横向滑动距离不够则返回原位置
                    else if (endX - startX > 0 && distanceY < distanceX) {
                        backOrigin(distanceX);
                    } else {
                        decorView.setX(0);
                    }
                    break;
            }

            //专门给有多滑动页面用
            if (startX < 20) {
                return false;
            }
        }
        return super.dispatchTouchEvent(event);
    }


    /**
     * 返回原点
     *
     * @param distanceX 横向滑动距离
     */
    private void backOrigin(float distanceX) {
        ObjectAnimator.ofFloat(decorView, "X", distanceX, 0).setDuration(300).start();
    }

    /**
     * 划出屏幕
     *
     * @param distanceX 横向滑动距离
     */
    private void moveOn(float distanceX) {

        canMove = false;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distanceX, screenWidth);
        valueAnimator.setDuration(300);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                decorView.setX((Float) animation.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
