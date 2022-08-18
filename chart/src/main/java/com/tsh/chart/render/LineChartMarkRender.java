package com.tsh.chart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

import com.tsh.chart.data.ChartData;
import com.tsh.chart.line.LineChartBase;

import java.util.List;

public class LineChartMarkRender<X extends LineChartBase.XAxis, E extends LineChartBase.Entry>
        extends Render<LineChartBase<X, E>> {
    protected final Paint markPaint;
    protected final TextPaint textPaint;

    public LineChartMarkRender(LineChartBase<X, E> chart) {
        super(chart);
        markPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void onDraw(Canvas canvas) {
        List<LineChartBase.EntrySet<E>> dataSets;
        ChartData<X, LineChartBase.EntrySet<E>> data = chart.getData();
        if (data != null && (dataSets = data.getData()) != null) {
            for (LineChartBase.EntrySet<E> dataSet : dataSets) {
                List<E> entries = dataSet.getEntries();
                for (int i = 0; i < entries.size(); i++) {
                    E entry = entries.get(i);
                    float markX = chart.getPaddingStart() + chart.getEntryWidth() * i;
                    if (markX >= chart.getStartDrawLeft() && markX <= chart.getStartDrawRight()) {
                        onDrawEntryMark(canvas, entry);
                    }
                }
            }
            for (LineChartBase.EntrySet<E> dataSet : dataSets) {
                List<E> entries = dataSet.getEntries();
                for (int i = 0; i < entries.size(); i++) {
                    E entry = entries.get(i);
                    float markX = chart.getPaddingStart() + chart.getEntryWidth() * i;
                    if (markX >= chart.getStartDrawLeft() && markX <= chart.getStartDrawRight()) {
                        onDrawOverEntryMark(canvas, entry);
                    }
                }
            }
        }
    }

    protected void onDrawOverEntryMark(Canvas canvas, E entry) {

    }

    protected void getEntryPos(E entry, float[] pos) {
        pos[0] = chart.getPaddingStart() + chart.getEntryWidth() * entry.getIndex();
        pos[1] = chart.transValueToY(entry.getValue());
    }

    protected void onDrawEntryMark(Canvas canvas, E entry) {
    }
}
