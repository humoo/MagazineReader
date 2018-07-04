package com.humo.magazinereader.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.humo.magazinereader.R;

/**
 * @author zhxumao
 * Created on 2018/7/5 0005 00:37.
 */
public class PagerActivity extends Activity {

    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pager);
        viewPager = findViewById(R.id.viewPager);

        MyPagerAdapter adapter = new MyPagerAdapter(this);
        viewPager.setAdapter(adapter);

    }
}
