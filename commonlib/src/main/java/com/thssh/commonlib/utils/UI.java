package com.thssh.commonlib.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author hutianhang
 */
public class UI {
    public static float dp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

    public static float sp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, Resources.getSystem().getDisplayMetrics());
    }
}
