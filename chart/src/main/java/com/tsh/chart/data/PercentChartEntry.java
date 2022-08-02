package com.tsh.chart.data;

public class PercentChartEntry extends ChartEntry {
    public PercentChartEntry(int color, float percent) {
        super(color);
        this.percent = percent;
    }

    private float percent;

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
