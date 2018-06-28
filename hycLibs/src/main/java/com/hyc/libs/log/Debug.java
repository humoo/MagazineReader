
package com.hyc.libs.log;

import android.util.Log;

import com.hyc.libs.Configure;

/**
 * @author zhxumao
 *         Created on 2017/10/31.
 */

public class Debug {

    private static final String SDK = "HycLib";

    public static void d(String tag, String msg) {
        if (Configure.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Configure.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (Configure.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void d(String msg) {
        if (Configure.DEBUG) {
            Log.d(SDK, msg);
        }
    }

    public static void e(String msg) {
        if (Configure.DEBUG) {
            Log.e(SDK, msg);
        }
    }

    public static void w(String msg) {
        if (Configure.DEBUG) {
            Log.w(SDK, msg);
        }
    }

    /**
     * 客户可见的日志
     */
    public static void Logi(String msg) {
        Log.i(SDK, msg);
    }

    /**
     * 客户可见的日志
     */
    public static void Loge(String msg) {
        Log.e(SDK, msg);
    }

}
