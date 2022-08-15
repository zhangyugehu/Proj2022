package com.tsh.chart.data;

import java.util.List;

public class ChartEntrySet<T extends IChartEntry> implements IChartEntry {
    protected CharSequence label;
    protected List<T> entries;
    protected int color;

    public ChartEntrySet(CharSequence label, List<T> entries) {
        this.label = label;
        this.entries = entries;
    }

    public List<T> getEntries() {
        return entries;
    }

    public void setEntries(List<T> entries) {
        this.entries = entries;
    }

    public CharSequence getLabel() {
        return label;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
