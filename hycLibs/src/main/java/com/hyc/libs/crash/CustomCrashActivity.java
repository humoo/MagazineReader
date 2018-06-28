package com.hyc.libs.crash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hyc.libs.HApplication;
import com.hyc.libs.utils.scalable.SharePrefUtil;

/**
 * @author zhxumao
 *         Created on 2018/1/13 0013 15:45.
 */

public class CustomCrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long lastCrashTime = SharePrefUtil.getLong("crashTime", 0);
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCrashTime < 1000 * 30) { // 60s 内发生第二次崩溃视为应用连续崩溃
            HApplication.crashInit(CrashActivity.class);
        } else {
//            startActivity(new Intent(CustomCrashActivity.this,MainActivity.class));
            SharePrefUtil.setLong("crashTime", currentTime);
        }
        finish();
    }
}
