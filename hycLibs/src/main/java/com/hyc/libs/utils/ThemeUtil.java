package com.hyc.libs.utils;

import android.content.Context;
import android.util.TypedValue;

import com.hyc.libs.R;

/**
 * @author zhxumao
 *         Created on 2017/12/29 0029 15:26.
 */

public class ThemeUtil {

    /**
     * 获取主题色
     */
    public int getColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取深主题色
     */
    public int getDarkColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

}
