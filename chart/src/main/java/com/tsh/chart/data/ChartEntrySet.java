package com.tsh.chart.data;

import java.util.List;

public class ChartEntrySet<T extends ChartEntry> implements IChartEntry {
    private List<T> entrySet;

    public ChartEntrySet(List<T> entrySet) {
        this.entrySet = entrySet;
    }

    public List<T> getEntrySet() {
        return entrySet;
    }

    public void setEntrySet(List<T> entrySet) {
        this.entrySet = entrySet;
    }
}
