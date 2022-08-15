package com.tsh.chart.render;

import android.graphics.Canvas;
import android.view.View;

public abstract class Render<V extends View> {
    public V chart;

    public Render(V chart) {
        this.chart = chart;
    }

    public abstract void onDraw(Canvas canvas);
}
