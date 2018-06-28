package com.humo.magazinereader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.humo.magazinereader.widget.MatrixImageView;
import com.hyc.libs.base.view.recyclerview.HRListener;
import com.hyc.libs.base.view.recyclerview.HRViewHolder;

/**
 * @author zhxumao
 * Created on 2018/6/29 0029 00:17.
 */
public class VH extends HRViewHolder {

    MatrixImageView largeLabel;

    public VH(Context context, ViewGroup root, HRListener listener) {
        super(context, root, R.layout.vh, listener);
    }

    @Override
    public void bindData(Object o, int position, int selectedP) {
        RandomColor randomColor = new RandomColor();

        int color = randomColor.randomColor();
        largeLabel.setBackgroundColor(color);
    }

    @Override
    protected void bindView(View rootView) {
        largeLabel = rootView.findViewById(R.id.largeLabel);

    }
}
