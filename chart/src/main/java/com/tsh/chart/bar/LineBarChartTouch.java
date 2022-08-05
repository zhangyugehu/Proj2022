package com.tsh.chart.bar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class LineBarChartTouch extends LineBarChartBase {
    public LineBarChartTouch(Context context) {
        this(context, null);
    }

    public LineBarChartTouch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineBarChartTouch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
