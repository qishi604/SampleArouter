package com.sc.basiclib.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public class ViewUtils {

    private static final DisplayMetrics DISPLAYMETRICS = Resources.getSystem().getDisplayMetrics();

    private static final float DENSITY = DISPLAYMETRICS.density;

    public static int dp(float v) {
        return (int) (DENSITY * v +0.5f);
    }
}
