package com.hyc.libs;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;

/**
 * @author zhxumao
 *         Created on 2017/12/27 0027 16:54.
 */

public class HApplication extends Application {

    //    private RefWatcher refWatcher;
    private static HApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        OkGoInit();

//        refWatcher = LeakCanary.install(this);

        crashInit(null);
    }

    /**
     * 网络请求框架Init
     * https://github.com/jeasonlzy/okhttp-OkGo/wiki/Init
     */
    private void OkGoInit() {
        OkGo.getInstance().init(this);
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static HApplication getHApplication() {
        return instance;
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        HApplication application = (HApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    /**
     * 本地异常捕获
     * 提供 CrashActivity,开发者可自行在manifest.xml进行配置
     *
     * @param clazz 发生异常时展示的Activity页面
     */
    public static void crashInit(Class<? extends Activity> clazz) {
//        CrashHelper.install(mContext, clazz);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
