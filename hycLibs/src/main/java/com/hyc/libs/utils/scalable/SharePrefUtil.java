package com.hyc.libs.utils.scalable;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hyc.libs.log.Debug;
import com.hyc.libs.utils.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.hyc.libs.HApplication.getAppContext;

/**
 * SP工具类
 * 建议复制到开发项目中
 * <p>
 * 需要更改 NAME , CACHE_NAME
 *
 * @author zhxumao
 *         Created on 2017/12/27 0027 16:54.
 */
public class SharePrefUtil {

    private static final String NAME = getAppContext().getPackageName()+"_SP_NAME";
    private static final String CACHE_NAME = getAppContext().getPackageName()+"_SP_CACHE_NAME";

    public static String getString(String key, String value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void setString(String key, String value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static long getLong(String KeyWord, long value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getLong(KeyWord, value);
    }

    public static void setLong(String KeyWord, long value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(KeyWord, value);
        edit.apply();
    }

    public static int getInt(String key, int value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    public static void setInt(String key, int value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static boolean getBoolean(String key, boolean value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static float getFloat(String key, float value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, value);
    }

    public static void setFloat(String key, float value) {
        SharedPreferences sp = getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(key, value);
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

    /**
     * 保存对象
     *
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     */
    public static void saveObject(String key, Object obj) {
        try {
            // 保存对象
            SharedPreferences.Editor sp = getAppContext().getSharedPreferences(CACHE_NAME, Context
                    .MODE_PRIVATE).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sp.putString(key, bytesToHexString);
            sp.apply();
        } catch (IOException e) {
            e.printStackTrace();
            Debug.d("保存缓存：" + "[" + key + "]" + " 失败");
        }
    }

    /**
     * desc:获取保存的Object对象
     */
    public static Object readObject(String key) {
        try {
            SharedPreferences sp = getAppContext().getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
            if (sp.contains(key)) {
                String string = sp.getString(key, "");
                if (!TextUtils.isEmpty(string)) {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringUtil.StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    return is.readObject();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Debug.d("读取缓存：" + "[" + key + "]" + " 失败");
        }
        //所有异常返回null
        return null;
    }
}
