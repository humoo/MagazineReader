package com.hyc.libs.widget.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyc.libs.R;

/**
 * @author zhxumao
 *         Created on 2017/12/25 0025 11:42.
 */

public class HLoadingView extends LinearLayout {

    enum Type{
        LOADING,LOADED,EMPTY,ERROR,NONET
    }

    private int loadImg;
    private int loadImgScale;
    private String loadText;
    private int loadTextColor;
    private float loadTextSize;

    private TextView textView;
    private ImageView imageView;


    public HLoadingView(Context context) {
        super(context);
    }

    public HLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.HLoadingView);
        loadImg  = typedArray.getResourceId(R.styleable.HLoadingView_loadImg,0);//需要更改默认图
        loadImgScale = typedArray.getInt(R.styleable.HLoadingView_loadImgScale,0);
        loadText = typedArray.getString(R.styleable.HLoadingView_loadText);
        loadTextColor = typedArray.getColor(R.styleable.HLoadingView_loadTextColor, Color.GRAY);
        loadTextSize = typedArray.getDimension(R.styleable.HLoadingView_loadTextSize,14);
        typedArray.recycle();

        textView = new TextView(context);
        textView.setText(loadText);
        textView.setTextColor(loadTextColor);
        textView.setTextSize(loadTextSize);

        imageView = new ImageView(context);
        imageView.setImageResource(loadImg);
//        imageView.setScaleType(loadImgScale);

        setGravity(Gravity.CENTER);
        addView(imageView);
        addView(textView);



    }

    public HLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showLoadingView(){
        setVisibility(VISIBLE);
    }

    public void showLoadErrorView(){
        setVisibility(VISIBLE);

    }

    public void showEmptyView(){
        setVisibility(VISIBLE);

    }

    public void showNoNetView(){
        setVisibility(VISIBLE);

    }

    public void loaded(){
        setVisibility(GONE);
    }




    public void setLoadImg(int loadImg) {
        this.loadImg = loadImg;
        imageView.setImageResource(loadImg);
    }

    public void setLoadImgScale(int loadImgScale) {
        this.loadImgScale = loadImgScale;
//        imageView.setScaleType(loadImgScale);

    }

    public void setLoadText(String loadText) {
        this.loadText = loadText;
        textView.setText(loadText);
    }

    public void setLoadTextColor(int loadTextColor) {
        this.loadTextColor = loadTextColor;
        textView.setTextColor(loadTextColor);
    }

    public void setLoadTextSize(float loadTextSize) {
        this.loadTextSize = loadTextSize;
        textView.setTextSize(loadTextSize);
    }
}
