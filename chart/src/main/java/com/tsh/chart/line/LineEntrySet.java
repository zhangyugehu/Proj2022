package com.tsh.chart.line;

import android.view.View;

import com.tsh.chart.data.ChartEntrySet;

import java.util.List;

public class LineEntrySet<E extends LineEntry> extends ChartEntrySet<E> {

    private float strokeWidth;
    private float highlightWidth;
    private int highlightColor;
    int highlightPointInnerColor;
    float highlightPointInnerRadius;
    int highlightPointOuterColor;
    float highlightPointOuterRadius;

    public LineEntrySet(CharSequence label, int color, List<E> entries) {
        super(label, entries);
        this.color = color;
        highlightWidth = 1;
        highlightColor = View.NO_ID;
        highlightPointInnerColor = View.NO_ID;
        highlightPointInnerRadius = -1;
        highlightPointOuterColor = View.NO_ID;
        highlightPointOuterRadius = -1;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public float getHighlightWidth() {
        return highlightWidth;
    }

    public void setHighlightWidth(float highlightWidth) {
        this.highlightWidth = highlightWidth;
    }

    public int getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }

    public int getHighlightPointInnerColor() {
        return highlightPointInnerColor;
    }

    public void setHighlightPointInnerColor(int highlightPointInnerColor) {
        this.highlightPointInnerColor = highlightPointInnerColor;
    }

    public float getHighlightPointInnerRadius() {
        return highlightPointInnerRadius;
    }

    public void setHighlightPointInnerRadius(float highlightPointInnerRadius) {
        this.highlightPointInnerRadius = highlightPointInnerRadius;
    }

    public int getHighlightPointOuterColor() {
        return highlightPointOuterColor;
    }

    public void setHighlightPointOuterColor(int highlightPointOuterColor) {
        this.highlightPointOuterColor = highlightPointOuterColor;
    }

    public float getHighlightPointOuterRadius() {
        return highlightPointOuterRadius;
    }

    public void setHighlightPointOuterRadius(float highlightPointOuterRadius) {
        this.highlightPointOuterRadius = highlightPointOuterRadius;
    }
}
