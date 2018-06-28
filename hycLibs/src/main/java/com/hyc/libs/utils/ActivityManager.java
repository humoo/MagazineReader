package com.hyc.libs.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * activity管理类
 *
 * @author zhxumao
 *         Created on 2017/12/28 0028 15:47.
 */

public class ActivityManager {

    private volatile Stack<Activity> activityStack = new Stack<Activity>();

    private static volatile ActivityManager instance;

    private ActivityManager() {
    }

    public synchronized static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 获得当前栈顶的Activity
     *
     * @return Activity Activity
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 将当前Activity推入栈中
     *
     * @param activity Activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 将当前Activity退出栈
     *
     * @param activity Activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 杀死栈中其他所有Activity，保留指定Activity
     *
     * @param cls Class 类名
     */
    public void finishOtherActivity(Class cls) {
        if (null == cls) {
            return;
        }

        for (Activity activity : activityStack) {
            if (null == activity || activity.getClass().equals(cls)) {
                continue;
            }
            activity.finish();
        }
    }

    /**
     * 退出指定栈中的Activity
     *
     * @param cls Class 类名
     */
    public void finishDirectActivity(Class cls) {
        if (null == cls) {
            return;
        }

        for (Activity activity : activityStack) {
            if (null != activity && activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 关闭当前Activity栈中.第二个activity
     */
    public void finishSecondActivity() {
        Activity activity = activityStack.get(activityStack.size() - 2);
        if (activity != null) {
            activity.finish();
            popActivity(activity);
        }
    }

    /**
     * 退出栈中所有Activity
     */
    public void finishAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            activity.finish();
            popActivity(activity);
        }
    }

    /**
     * 查看目标Activity是否活动中
     */
    public boolean isActivityAlive(Class cls) {
        if (null == cls) {
            return false;
        }
        for (Activity activity : activityStack) {
            if (null == activity) {
                continue;
            }
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }
}
