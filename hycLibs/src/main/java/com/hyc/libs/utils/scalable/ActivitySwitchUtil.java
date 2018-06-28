package com.hyc.libs.utils.scalable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

/**
 * Activity 跳转工具类
 *
 * @author zhxumao
 *         Created on 2018/1/2 0002 15:08.
 */

public class ActivitySwitchUtil {

    public static void openNewActivity(Activity activity, Class target) {
        openNewActivity(activity, target, false);
    }

    public static void openNewActivity(Activity activity, Class target, boolean finish) {
        openNewActivity(activity, target, null, null, finish);
    }

    /**
     * @param activity
     * @param target    目标Activity
     * @param bundleKey bundle参数的key
     * @param bundle
     * @param finish    是否关闭当前Activity
     */
    public static void openNewActivity(Activity activity, Class target, String bundleKey, Bundle bundle, boolean finish) {
        if (activity == null || target == null) {
            return;
        }
        Intent intent = new Intent(activity, target);
        if (!TextUtils.isEmpty(bundleKey) && bundle != null) {
            intent.putExtra(bundleKey, bundle);
        }
        activity.startActivity(intent);
        if (finish) {
            activity.finish();
        }
    }

    public static void openNewActivityForResult(Activity activity, Class target, int requestCode) {
        openNewActivityForResult(activity, target, requestCode, false);
    }

    public static void openNewActivityForResult(Activity activity, Class target, int requestCode, boolean finish) {
        openNewActivityForResult(activity, target, null, null, requestCode, finish);
    }

    /**
     * @param activity
     * @param target      目标Activity
     * @param bundleKey   bundle参数的key
     * @param bundle
     * @param requestCode
     * @param finish      是否关闭当前Activity
     */
    public static void openNewActivityForResult(Activity activity, Class target, String bundleKey, Bundle bundle, int requestCode, boolean finish) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, target);
        if (!TextUtils.isEmpty(bundleKey) && bundle != null) {
            intent.putExtra(bundleKey, bundle);
        }
        activity.startActivityForResult(intent, requestCode);
        if (finish) {
            activity.finish();
        }
    }

    /**
     * @param fragment
     * @param target      目标Activity
     * @param bundleKey   bundle参数的key
     * @param bundle
     * @param requestCode
     * @param finish      是否关闭当前Activity
     */
    public static void openNewActivityForResultByFragment(Fragment fragment, Class target, String bundleKey, Bundle bundle, int requestCode, boolean finish) {
        if (fragment == null || fragment.getActivity() == null) {
            return;
        }
        Intent intent = new Intent(fragment.getActivity(), target);
        if (!TextUtils.isEmpty(bundleKey) && bundle != null) {
            intent.putExtra(bundleKey, bundle);
        }
        fragment.startActivityForResult(intent, requestCode);
        if (finish) {
            fragment.getActivity().finish();
        }
    }


}
