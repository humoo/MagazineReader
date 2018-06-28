package com.hyc.libs.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author zhxumao
 *         Created on 2018/1/3 0003 10:04.
 */

public class ScreenUtil {

    /**
     * 获取当前屏幕 bitmap
     *
     * @param activity
     * @return
     */
    public static Bitmap getScreenBitmap(Activity activity) {
        //计算屏幕大小
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int w = metrics.widthPixels;
        int h = metrics.heightPixels;
        //创建屏幕大小的位图
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ALPHA_8);
        //获取当前屏幕视图
        View view = activity.getWindow().getDecorView();
        //将视图绘制到bitmap
        view.draw(new Canvas(bitmap));
        return bitmap;
    }


}
