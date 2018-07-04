package com.humo.magazinereader.viewpager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.humo.magazinereader.R;

import java.io.InputStream;
import java.util.LinkedList;

/**
 * @author zhxumao
 * Created on 2018/7/5 0005 00:22.
 */
public class MyPagerAdapter extends PagerAdapter {

    LinkedList<View> mCaches = new LinkedList<View>();
    Context context;

    public MyPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewHolder viewHolder = null;
        View convertView = null;
        if (mCaches.size() == 0) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item,
                    null, false);
            ImageView textView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder = new ViewHolder();
            viewHolder.imageView = textView;
            convertView.setTag(viewHolder);
        } else {
            convertView = mCaches.removeFirst();
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String path;
        if (position < 9) {
            path = "000" + (position + 1) + ".jpg";
        } else {
            path = "00" + (position + 1) + ".jpg";
        }

        //这里才是重点
        AssetManager assetManager = context.getAssets();
        try {
            InputStream in = assetManager.open(path);
            Bitmap bmp = BitmapFactory.decodeStream(in);
            viewHolder.imageView.setImageBitmap(bmp);
        } catch (Exception e) {
// TODO: handle exception
        }
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return convertView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mCaches.add(contentView);
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
