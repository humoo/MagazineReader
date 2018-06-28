package com.hyc.libs.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.hyc.libs.base.BaseFragment;

import java.util.List;

/**
 * @author zhxumao
 *         Created on 2018/1/5 0005 13:08.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;
    private String[] mTitles;

    public TabViewPagerAdapter(FragmentManager fm, List<BaseFragment> mFragments, String[] titles) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}

