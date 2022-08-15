package com.tsh.chart.line;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tsh.chart.render.Render;
import com.tsh.chart.data.ChartData;

public class LineChart<X extends LineChartBase.XAxis, E extends LineChartBase.Entry> extends HorizontalScrollView {
    LineChartBase<X, E> lineChartBase;

    public LineChart(@NonNull Context context) {
        this(context, null);
    }

    public LineChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChart(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
        addView((lineChartBase = new LineChartBase<>(context, attrs, defStyleAttr)),
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setData(ChartData<X, LineChartBase.EntrySet<E>> data) {
        lineChartBase.setData(data);
        lineChartBase.invalidate();
    }

    public LineChartBase<X, E> getLineChartBase() {
        return lineChartBase;
    }
}
