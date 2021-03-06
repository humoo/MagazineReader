package com.hyc.libs.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hyc.libs.R;

/**
 * @author zhxumao
 *         Created on 2018/1/16 0016 17:33.
 */

public class AnnularProgress extends View {

    private int mCurrent = 0;//当前进度
    private Paint mPaintCurrent;
    private float mPaintWidth;//画笔宽度
    private int mPaintColor = Color.RED;//画笔颜色
    private int location;//从哪个位置开始
    private float startAngle;//开始角度
    private int TIME = 10;//默认每隔10ms重绘一次
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {
                handler.postDelayed(this, TIME);
                mCurrent++;
                if (mCurrent == 100) {
                    mCurrent = -100;
                }
                invalidate();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };


    public AnnularProgress(Context context) {
        this(context, null);
    }

    public AnnularProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnnularProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AnnularProgress);
        location = array.getInt(R.styleable.AnnularProgress_progress_location, 2);
        mPaintWidth = array.getDimension(R.styleable.AnnularProgress_progress_paint_width, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
        mPaintColor = array.getColor(R.styleable.AnnularProgress_progress_paint_color, mPaintColor);
        array.recycle();

        initPaint();
    }

    private void initPaint() {
        //画笔->进度圆弧
        mPaintCurrent = new Paint();
        mPaintCurrent.setAntiAlias(true);
        mPaintCurrent.setStrokeWidth(mPaintWidth);
        mPaintCurrent.setStyle(Paint.Style.STROKE);
        mPaintCurrent.setColor(mPaintColor);
        mPaintCurrent.setStrokeCap(Paint.Cap.ROUND);

        if (location == 1) {
            startAngle = -180;
        } else if (location == 2) {//默认从上侧开始
            startAngle = -90;
        } else if (location == 3) {
            startAngle = 0;
        } else if (location == 4) {
            startAngle = 90;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制当前进度
        RectF rectF = new RectF(mPaintWidth / 2, mPaintWidth / 2, getWidth() - mPaintWidth / 2, getHeight() - mPaintWidth / 2);
        float sweepAngle = 360 * mCurrent / 100;
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mPaintCurrent);
    }

    /**
     * 开始
     */
    public void start() {
        handler.postDelayed(runnable, TIME); //每隔TIMEms执行
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
