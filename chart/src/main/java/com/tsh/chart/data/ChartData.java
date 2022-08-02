package com.tsh.chart.data;

import java.util.List;

public class ChartData<T extends ChartEntry> {
    List<CharSequence> xValues;
    List<T> data;

    public List<CharSequence> getxValues() {
        return xValues;
    }

    public void setxValues(List<CharSequence> xValues) {
        this.xValues = xValues;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
