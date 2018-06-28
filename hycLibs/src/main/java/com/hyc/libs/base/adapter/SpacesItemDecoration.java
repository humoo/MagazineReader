package com.hyc.libs.base.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hyc.libs.log.Debug;

/**
 * Created by zhxumao on 2018/4/26.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int screenWidth, dp, itemWidth;
    private int screenItem;
    private int space;

    public SpacesItemDecoration(int screenItem, int screenWidth, int dp, int itemWidth) {
        this.dp = dp;
        this.itemWidth = itemWidth;
        this.screenWidth = screenWidth;
        this.screenItem = screenItem;

        space = (screenWidth - itemWidth * screenItem - dp * 2) / ((screenItem - 1) * 2);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildLayoutPosition(view) % screenItem == 0) {
            outRect.left = 0;
        } else {
            outRect.left = space;
        }

        if ((parent.getChildLayoutPosition(view) + 1) % screenItem == 0) {
            outRect.right = 0;
        } else {
            outRect.right = space;
        }


//        outRect.top = t;
//        outRect.bottom = b; // Add top margin only for the first item to avoid double space between items if (parent.getChildPosition(view) == 0) outRect.top = space; }
    }
}
