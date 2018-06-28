package com.hyc.libs.utils.scalable;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hyc.libs.utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 需要新增升级工具类 在这里写
 *
 * @author zhxumao
 *         Created on 2018/2/5 0005 15:03.
 */

public class ScalableUtils {

    /**
     * 高德地图应用包名
     */
    public static final String AMAP_PACKAGENAME = "com.autonavi.minimap";
    /**
     * 百度地图应用包名
     */
    public static final String BAIDUMAP_PACKAGENAME = "com.baidu.BaiduMap";

    /**
     * 获取状态栏高度 - ANDROID 4.4以上
     * <p>
     * 4.4 以下状态栏
     *
     * @param context
     * @return
     */
    public static int getStatusHeight19(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight1 = -1;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
            }
            return statusBarHeight1;
        }
        return 0;
    }


    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusBarHeight1 = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideSoftInput(Activity context) {
        if (context == null) return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 展示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context, View v) {
        if (context == null) return;
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 复制到剪贴板
     *
     * @param context
     */
    public static void clipBoard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }

    /**
     * 是否已安装高德地图
     */
    public static boolean isInstallGaode() {
        return new File("/data/data/" + AMAP_PACKAGENAME).exists();
    }

    /**
     * 是否已安装百度地图
     */
    public static boolean isInstallBaidu() {
        return new File("/data/data/" + BAIDUMAP_PACKAGENAME).exists();
    }

    /**
     * 打开百度地图路径规划
     * http://lbsyun.baidu.com/index.php?title=uri/api/android
     * 现在使用的是高德地图是经纬度数据 火星坐标：gcj02
     * 百度地图的经纬度类型：默认为bd09经纬度坐标。允许的值为bd09ll、bd09mc、gcj02、wgs84。bd09ll表示百度经纬度坐标，gcj02表示经过国测局加密的坐标，wgs84表示gps获取的坐标。
     */
    public static void openBaiduMap(Activity activity, String title, double lat, double lng) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?" + "destination=" + lat + "," + lng + "&name:" + title + "&mode=driving&coord_type=gcj02"));
        activity.startActivity(intent);
    }

    /**
     * 打开高德地图路径规划
     * <p>
     * http://lbs.amap.com/api/amap-mobile/guide/android/route
     *
     * @param activity
     * @param name     目的地名称
     * @param lat      纬度
     * @param lng      经度
     */
    public static void openGaoDeMap(Activity activity, String name, double lat, double lng) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("amapuri://route/plan/?dlat=" + lat + "&dlon=" + lng + "&dname=" + name + "&dev=0&t=0"));
        activity.startActivity(intent);
    }

    /**
     * 打开高德网页路径规划
     * http://lbs.amap.com/api/uri-api/guide/travel/route
     *
     * @param activity
     * @param clat
     * @param clng
     * @param dlat
     * @param dlng
     */
    public static void openGaodeWeb(Activity activity, double clat, double clng, double dlat, double dlng) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        // 驾车导航
        intent.setData(Uri.parse("http://uri.amap.com/navigation?from=" + clng + "," + clat + "&to=" + dlng + "," + dlat + "&mode=car&src=全民庄园"));
        activity.startActivity(intent);
    }

    /**
     * 位图转数组
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转换为dip
     *
     * @param value
     * @param context
     * @return
     */
    public static float float2Dip(float value, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

//    /**
//     * Glide默认配置
//     *
//     * @return
//     */
//    public static RequestOptions getGlideDefaultOptions() {
//        return new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.mis_default_error)
//                .error(R.mipmap.mis_default_error)
//                .priority(Priority.HIGH);
//    }
//
//    /**
//     * Glide默认配置
//     *
//     * @return
//     */
//    public static RequestOptions getGlideDefaultOptions4Goods() {
//        return new RequestOptions()
//                .fitCenter()
//                .placeholder(R.mipmap.mis_default_error)
//                .error(R.mipmap.mis_default_error)
//                .priority(Priority.HIGH);
//    }

    /**
     * 获取当天日期
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getCurDay() {
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        return s.format(date);
    }

    /**
     * 获取时间间隔
     *
     * @param targetTime
     * @param defaultInteval
     * @return
     */
    public static long getTimeInteval(String targetTime, long defaultInteval) {
        long inteval = 0;
        if (!StringUtil.isFine(targetTime)) {
            return defaultInteval;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date;
            date = format.parse(targetTime);
            inteval = System.currentTimeMillis() - date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return defaultInteval;
        }
        return inteval;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static double change(double a) {
        return a * Math.PI / 180;
    }

    public static double changeAngle(double a) {
        return a * 180 / Math.PI;
    }
}
