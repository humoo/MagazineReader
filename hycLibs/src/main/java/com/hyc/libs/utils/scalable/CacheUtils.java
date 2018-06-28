package com.hyc.libs.utils.scalable;

import android.content.Context;
import android.content.SharedPreferences;

import static com.hyc.libs.HApplication.getAppContext;

/**
 * 缓存工具
 * Created by zhxumao on 2018/3/29.
 */

public class CacheUtils {

    public static final String HOME_INDEX = "HOME_INDEX";
    public static final String STORE_CATEGORY = "STORE_CATEGORY";
    public static final String STORE_GOODS_LIST = "STORE_GOODS_LIST";
    public static final String PROFILE = "PROFILE";

    private static final String NAME = getAppContext().getPackageName() + "_CACHE_NAME_RR_FARM";

    public static String getString(String key, String defValue) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void setString(String key, String value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public static void remove(String key) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();
    }

    /**
     * 清除整一个
     */
    public static void clear() {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
