package com.tsh.chart.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.tsh.chart.R;

import java.util.List;

public class LabelPieChart extends PieChart {

    public interface LabelFormatter {
        CharSequence format(Entry entry, int position);
    }

    private final TextPaint textPaint;
    private Entry primaryEntry;
    int primaryPosition = -1;
    LabelFormatter labelFormatter;
    float textSize;
    Typeface typeface;

    public LabelPieChart(Context context) {
        this(context, null);
    }

    public LabelPieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelPieChart)){
            textSize = typedArray.getDimension(R.styleable.LabelPieChart_pieTextSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
            if (typedArray.hasValue(R.styleable.LabelPieChart_pieTypeface)) {
                int fontId = typedArray.getResourceId(R.styleable.LabelPieChart_pieTypeface, -1);
                typeface = ResourcesCompat.getFont(context, fontId);
            }
        }
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setLabelFormatter(LabelFormatter labelFormatter) {
        this.labelFormatter = labelFormatter;
    }

    @Override
    public void addEntry(Entry entry) {
        super.addEntry(entry);
        if (entry.isPrimary) {
            primaryEntry = entry;
            primaryPosition = entries.size() - 1;
        }
    }

    @Override
    public void removeEntry(Entry entry) {
        super.removeEntry(entry);
        if (entry == primaryEntry) {
            primaryEntry = null;
            primaryPosition = -1;
        }
    }

    @Override
    public void setData(@NonNull List<Entry> entries) {
        super.setData(entries);
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            if (entry.isPrimary) {
                primaryEntry = entry;
                primaryPosition = i;
                break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (primaryEntry != null && labelFormatter != null) {
            textPaint.setColor(primaryEntry.color);
            textPaint.setTextSize(textSize);
            CharSequence label = labelFormatter.format(primaryEntry, primaryPosition);
            canvas.drawText(label, 0, label.length(), circleRectF.centerX(), textCenterY(circleRectF, textPaint),
                    textPaint);
        }
    }

    public void setTypeface(Typeface typeface) {
        textPaint.setTypeface(typeface);
        invalidate();
    }

    /**
     * 获取文字在 {@param rect} 中垂直居中 绘制时 y位置
     * @param rect
     * @param paint
     * @return
     */
    public static float textCenterY(RectF rect, Paint paint) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return rect.centerY() + (metrics.bottom - metrics.top) / 2 - metrics.bottom;
    }
}
