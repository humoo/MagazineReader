package com.hyc.libs.base.view.listview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础适配器
 * Created by zhxumao on 2017/11/3 18:15.
 */

public abstract class HLAdapter<T> extends BaseAdapter {

    private List<T> dataList = new ArrayList<>();
    private int selectedP = -1;

    public HLAdapter() {
    }

    public void addMore(List<T> data) {
        if (data != null && data.size() > 0) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void setSelected(int position) {
        selectedP = position;
        notifyDataSetChanged();
    }

    public void clear() {
        if(dataList != null) {
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HLViewHolder holder;
        if (convertView == null) {
            holder = getHolder();
            convertView = holder.getRootView(parent);
            convertView.setTag(holder);
        } else {
            holder = (HLViewHolder) convertView.getTag();
        }

        ((HLViewHolder) holder).bindData(dataList.get(position), position, selectedP);
        return convertView;
    }

    public abstract HLViewHolder getHolder();
}
