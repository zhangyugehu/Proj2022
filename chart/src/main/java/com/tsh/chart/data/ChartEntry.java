package com.tsh.chart.data;

public abstract class ChartEntry {
    public ChartEntry(int color) {
        this.color = color;
    }

    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
