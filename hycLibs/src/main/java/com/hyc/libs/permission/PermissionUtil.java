package com.hyc.libs.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


/**
 * 权限工具
 * Created by zhengxumao on 2016/10/27 0027.
 */
public abstract class PermissionUtil {

    public static int REQUEST_CONTACTS;

    /***
     * 检查单个权限
     *
     * @param context
     * @param pName   例：android.permission.ACCESS_COARSE_LOCATION
     */
    public static boolean checkPermission(Activity context, String pName) {

        if (ActivityCompat.checkSelfPermission(context, pName) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 申请权限
     *
     * @param context
     * @param pName
     * @param code
     */
    public static void requestPermission(Activity context, String[] pName, int code) {

        REQUEST_CONTACTS = code;
        ActivityCompat.requestPermissions(context, pName, REQUEST_CONTACTS);
    }


    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
