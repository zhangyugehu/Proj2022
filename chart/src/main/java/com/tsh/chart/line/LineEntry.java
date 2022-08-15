package com.tsh.chart.line;

import android.view.View;

import com.tsh.chart.data.IChartEntry;

public class LineEntry implements IChartEntry {
    int index;
    float value;

    public LineEntry(int index, float value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getValue() {
        return value;
    }

}