package com.tsh.chart.data;

import java.util.List;

public class ChartData<X, T extends IChartEntry> {
    List<X> xValues;
    List<T> data;

    public List<X> getxValues() {
        return xValues;
    }

    public void setxValues(List<X> xValues) {
        this.xValues = xValues;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
