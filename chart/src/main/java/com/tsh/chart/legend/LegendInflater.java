package com.tsh.chart.legend;

import android.content.Context;
import android.util.Pair;
import android.view.View;

import com.tsh.chart.data.ChartEntry;

public interface LegendInflater<T extends ChartEntry> {
    View inflate(Context context, Pair<CharSequence, T> data, int index, boolean hide);
}
