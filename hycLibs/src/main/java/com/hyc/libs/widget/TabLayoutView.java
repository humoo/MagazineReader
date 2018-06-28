package com.hyc.libs.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyc.libs.R;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


/**
 * TabLayout + viewpager,支持設置紅點和未讀消息
 *
 * @author zhxumao
 *         Created on 2018/1/4 0004 14:44.
 */

public class TabLayoutView extends FrameLayout {

    private List<Class> fragmentList = new ArrayList<>();
    private List<Object> fragmentParamList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private int[] normalDrawable;
    private int[] selectedDrawable;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;
    private MyPagerAdapter adapter;
    private int normalColor;
    private int selectedColor;

    /**
     * 未读数最高值
     */
    private int unReadLimit = 99;

    public TabLayoutView(Context context) {
        super(context);
        init(context);
    }

    public TabLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(context, R.layout.tab_layout_view_top, this);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * 添加视图
     *
     * @param fragment
     */
    public TabLayoutView addPagerView(Class fragment, Object param) {
        fragmentList.add(fragment);
        fragmentParamList.add(param);
        return this;
    }

    /**
     * 添加视图
     *
     * @param fragments
     */
    public TabLayoutView addPagerViews(List<Class> fragments) {
        fragmentList.addAll(fragments);
        return this;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabLayoutView build(FragmentManager fragmentManager) {

        if (titleList.size() == 0 || fragmentList.size() == 0) {
            return this;
        }

        adapter = new MyPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabSelect(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return this;
    }

    /**
     * 添加tab标题
     *
     * @param titles
     */
    public TabLayoutView addTitle(List<String> titles) {
        titleList.addAll(titles);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(getMyTabCustomView(titles.get(i)));
        }
        return this;
    }

    public TabLayoutView addTitleDrawable(int[] normalDrawable, int[] selectedDrawable) {
        this.normalDrawable = normalDrawable;
        this.selectedDrawable = selectedDrawable;
        return this;
    }

    public int getTabPosition() {
        return tabLayout.getSelectedTabPosition();
    }

    public void setUnReadLimit(int limit) {
        this.unReadLimit = limit;
    }

    /**
     * 设置小红点或未读数
     *
     * @param position 要修改的tab的位置
     * @param number   -1 取消显示， 0 显示小红点, n 显示数字为n的未读数
     */
    public void setUnReadSolid(int position, int number) {

        TabLayout.Tab tab = tabLayout.getTabAt(position);
        // 更新Badge前,先remove原来的customView,否则Badge无法更新
        if (tab == null) {
            return;
        }

        View customView = tab.getCustomView();
        if (customView != null) {
            ViewParent parent = customView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(customView);
            }
        }

        View tabView = getMyTabCustomView(titleList.get(position));
        TextView title = (TextView) tabView.findViewById(R.id.tv_tab_title);
        TextView solid = (TextView) tabView.findViewById(R.id.iv_tab_solid);
        TextView unRead = (TextView) tabView.findViewById(R.id.iv_tab_unread);
        unRead.setVisibility(GONE);
        solid.setVisibility(GONE);

        if (tab.isSelected()) {
            title.setTextColor(selectedColor);
        } else {
            title.setTextColor(normalColor);
        }

        if (number == -1) {

        } else if (number > unReadLimit) {
            unRead.setVisibility(VISIBLE);
            unRead.setText(String.valueOf(unReadLimit + "+"));
        } else if (number == 0) {
            solid.setVisibility(VISIBLE);
        } else {
            unRead.setVisibility(VISIBLE);
            unRead.setText(String.valueOf(number));
        }
        tab.setCustomView(tabView);
    }

    private void changeTabSelect(TabLayout.Tab tab, boolean isSelected) {

        View view = tab.getCustomView();
        if (view == null) {
            return;
        }
        TextView title = (TextView) view.findViewById(R.id.tv_tab_title);
        if (isSelected) {
            title.setTextColor(selectedColor);
        } else {
            title.setTextColor(normalColor);
        }
    }

    /**
     * 添加文字颜色，有自定义视图下使用
     *
     * @param normal
     * @param selected
     * @return
     */
    public TabLayoutView addTabSelectColor(int normal, int selected) {
        this.normalColor = normal;
        this.selectedColor = selected;
        addTabTextColor(normal, selected);
        return this;
    }

    /**
     * 添加文字颜色（无使用自定义视图下使用）
     *
     * @param normalColor
     * @param selectedColor
     * @return
     */
    public TabLayoutView addTabTextColor(int normalColor, int selectedColor) {
        tabLayout.setTabTextColors(normalColor, selectedColor);
        return this;
    }


    /**
     * 得到自定义视图
     *
     * @param s
     * @return
     */
    private View getMyTabCustomView(String s) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.tablayout_title, null);
        TextView tabTitle = (TextView) tabView.findViewById(R.id.tv_tab_title);
        tabTitle.setText(s);
        return tabView;
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        Object[] objectList;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            objectList = new Object[titleList.size()];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            Object o = objectList[position];
            if (o == null) {
                Class clazz = fragmentList.get(position);
                try {
                    if (fragmentParamList.isEmpty()) {
                        Constructor constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        o = constructor.newInstance();
                    } else {
                        Constructor constructor = clazz.getDeclaredConstructor(Object.class);
                        constructor.setAccessible(true);
                        o = constructor.newInstance(fragmentParamList.get(position));
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                objectList[position] = o;
            }
            return (Fragment) o;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            objectList[position] = null;
        }
    }


}
