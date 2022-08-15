package com.tsh.chart.bar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.Mapping;
import com.tsh.chart.R;
import com.tsh.chart.ViewWithKit;
import com.tsh.chart.data.ChartData;
import com.tsh.chart.data.ChartEntrySet;
import com.tsh.chart.data.PercentChartEntry;

import java.util.List;
import java.util.Locale;

public class LineBarChartBase<X extends LineBarChartBase.XAxis> extends ViewWithKit implements IAnimatorChart {
    private static final boolean SHOW_DEBUG_VIEW = false;

    public interface XAxis {
        CharSequence toReadable();
    }

    public interface YAxisFormatter<T> {
        CharSequence format(T data);
    }

    public interface OnSelectListener {
        void onSelected(int index, int subIndex);
    }

    public static class EntrySet extends ChartEntrySet<PercentChartEntry> {
        public EntrySet(CharSequence label, List<PercentChartEntry> entrySet) {
            super(label, entrySet);
        }
    }

    public interface AxisFormatter {
        CharSequence format(XAxis value);
    }

    ChartData<X, EntrySet> data;

    final Paint chartPaint;
    final TextPaint axisPaint;

    float xAxisTextSize;
    int xAxisTextColor;
    int xAxisHighlightColor;
    float xAxisHighlightMargin;
    float xAxisHighlightRadius;
    int xAxisBackgroundColor;
    float xAxisItemWidth;
    float xAxisSpacing;
    float xAxisEntrySpacing;
    float xAxisPaddingStart;
    float xAxisPaddingEnd;
    float xWidthHalf;
    float xAxisEntryWidth;

    float yAxisTextSize;
    float yAxisLineWidth;
    int yAxisLineColor;
    int yAxisTextColor;
    float yAxisRemained;
    float yAxisMax = 0, yAxisMin = 0;
    float yAxisMaxWithFactor;
    float yAxisFactor = 1.2f;
    int yAxisCount = 4;
    /**
     * y 轴指示线
     */
    boolean yAxisLine = true;
    RectF yAxisRect;

    final Mapping mapping;
    final Mapping yAxisValueMapping;
    final int[] touchIndex;

    final RectF entryRect;

    final GestureDetector gestureDetector;

    YAxisFormatter<Float> yAxisFormatter = data -> String.format(Locale.US, "%.0f%%", data * 100);
    OnSelectListener onSelectListener;
    float animatorPercent = 1;

    public LineBarChartBase(Context context) {
        this(context, null);
    }

    public LineBarChartBase(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineBarChartBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mapping = new Mapping();
        yAxisValueMapping = new Mapping();
        touchIndex = new int[] {-1, -1};
        gestureDetector = new GestureDetector(context, highlightGestureDetector);
        entryRect = new RectF();
        axisPaint.setTextAlign(Paint.Align.CENTER);

        xAxisBackgroundColor = ContextCompat.getColor(context, R.color.chart_background_color);
        xAxisTextColor = ContextCompat.getColor(context, R.color.chart_legend_text_color);
        xAxisHighlightColor = ContextCompat.getColor(context, R.color.chart_active_color);
        xAxisPaddingStart = 0;//dp(10);
        xAxisPaddingEnd = 0;//dp(10);
        xAxisHighlightMargin = dp(1);
        xAxisHighlightRadius = dp(2);

        yAxisTextColor = ContextCompat.getColor(context, R.color.chart_legend_text_color);
        yAxisLineColor = ContextCompat.getColor(context, R.color.chart_background_color);
        xAxisTextSize = sp(10);
        yAxisTextSize = sp(10);
        yAxisLineWidth = 1;
        xAxisSpacing = dp(5);
        xAxisEntrySpacing = dp(1);
        yAxisRemained = dp(30);
        yAxisRect = new RectF();
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public void setData(ChartData<X, EntrySet> data) {
        this.data = data;
        List<EntrySet> entrySets;
        if (data != null && (entrySets = data.getData()) != null) {
            calcMaxMin(entrySets);
            animatorPercent = yAxisMax;
        }
    }

    public ChartData<X, EntrySet> getData() {
        return data;
    }

    public void setAnimatorPercent(float animatorPercent) {
        this.animatorPercent = animatorPercent;
        invalidate();
    }

    public void showAnimator() {
        if (data != null) {
            getAnimator().start();
        }
    }

    @Override
    public Animator getAnimator() {
        return ObjectAnimator.ofFloat(this, "animatorPercent", 0, yAxisMax)
                .setDuration(DEFAULT_ANIMATION);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void unFocusHighlight() {
        touchIndex[0] = -1;
        touchIndex[1] = -1;
        if (onSelectListener != null) {
            onSelectListener.onSelected(-1, -1);
        }
        invalidate();
    }

    public void focusHighlight(int index) {
        touchIndex[0] = index;
        touchIndex[1] = -1;
        if (onSelectListener != null) {
            onSelectListener.onSelected(touchIndex[0], touchIndex[1]);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            xToIndex(event.getX(), touchIndex);
            if (onSelectListener != null) {
                onSelectListener.onSelected(touchIndex[0], touchIndex[1]);
            }
            postInvalidate();
        }
        return super.onTouchEvent(event);
    }

    final GestureDetector.SimpleOnGestureListener highlightGestureDetector = new GestureDetector.SimpleOnGestureListener() {
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawBackground(canvas);
        onDrawAxis(canvas);
        onDrawEntries(canvas);
    }

    private void onDrawBackground(Canvas canvas) {

    }

    RectF xAxisRect = new RectF();

    protected void onDrawAxis(Canvas canvas) {
        int viewCenterY = getHeight() >> 1;
        // xAxis background
        axisPaint.setColor(xAxisTextColor);
        axisPaint.setTextSize(xAxisTextSize);
        axisPaint.getFontMetrics(centerMetrics);
        float textHeightHalf = (centerMetrics.bottom - centerMetrics.top) / 2;
        xAxisRect.set(getPaddingStart(),
                viewCenterY - textHeightHalf,
                getWidth() - getPaddingEnd(),
                viewCenterY + textHeightHalf);
        float xAxisTextCenterY = textCenterY(xAxisRect.centerY(), axisPaint);
        chartPaint.setColor(xAxisBackgroundColor);
        chartPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(xAxisRect, chartPaint);
        if (data != null && data.getData() != null) {
            // yAxis
            axisPaint.setTextSize(yAxisTextSize);
            axisPaint.setColor(yAxisTextColor);
            axisPaint.getFontMetrics(centerMetrics);
            float yAxisTextHeight = centerMetrics.bottom - centerMetrics.top;
            axisPaint.setTextAlign(Paint.Align.LEFT);
            float availableHeightHalf = (getHeight()
                    - yAxisTextHeight
                    - getPaddingTop()
                    - getPaddingBottom()) / 2;
            yAxisMaxWithFactor = Math.max(Math.abs(yAxisMax), Math.abs(yAxisMin)) * yAxisFactor;
            yAxisValueMapping.setSrc(yAxisMaxWithFactor, -yAxisMaxWithFactor);
            yAxisValueMapping.setDest(yAxisTextHeight / 2, getHeight() - yAxisTextHeight / 2);
            mapping.setDest(yAxisTextHeight / 2, availableHeightHalf);
            mapping.setSrc(0, yAxisMaxWithFactor);
            float stepYAxisValue = yAxisMaxWithFactor / yAxisCount;
            CharSequence horizonText = yAxisFormatter.format(0f);
            float nextYAxisValue = 0;
            canvas.drawText(horizonText, 0, horizonText.length(), getPaddingStart(), xAxisTextCenterY, axisPaint);
            for (int i = 0; i < yAxisCount; i++) {
                nextYAxisValue += stepYAxisValue;
                CharSequence upText = yAxisFormatter.format(nextYAxisValue);
                CharSequence belowText = yAxisFormatter.format(-nextYAxisValue);
                float extraHeight = mapping.mapValue(nextYAxisValue);
                float upHorizonY = xAxisTextCenterY - extraHeight;
                float belowHorizonY = xAxisTextCenterY + extraHeight;
                axisPaint.getFontMetrics(centerMetrics);
                axisPaint.setStrokeWidth(yAxisLineWidth);
                if (yAxisLine) {
                    axisPaint.setColor(yAxisLineColor);
                    yAxisRect.set(getPaddingStart() + yAxisRemained,
                            viewCenterY - extraHeight - yAxisTextHeight / 2,
                            getWidth() - getPaddingEnd(),
                            viewCenterY - extraHeight + yAxisTextHeight / 2);
                    canvas.drawLine(yAxisRect.left, yAxisRect.centerY(), yAxisRect.right, yAxisRect.centerY(), axisPaint);
                }
                axisPaint.setColor(yAxisTextColor);
                canvas.drawText(upText, 0, upText.length(), getPaddingStart(), upHorizonY, axisPaint);
                if (yAxisLine) {
                    axisPaint.setColor(yAxisLineColor);
                    yAxisRect.set(getPaddingStart() + yAxisRemained,
                            viewCenterY + extraHeight - yAxisTextHeight / 2,
                            getWidth() - getPaddingEnd(),
                            viewCenterY + extraHeight + yAxisTextHeight / 2);
                    canvas.drawLine(yAxisRect.left, yAxisRect.centerY(), yAxisRect.right, yAxisRect.centerY(), axisPaint);
                }
                axisPaint.setColor(yAxisTextColor);
                canvas.drawText(belowText, 0, belowText.length(), getPaddingStart(), belowHorizonY, axisPaint);
            }
        }
        if (data != null && data.getxValues() != null) {
            // xAxis
            axisPaint.setTextAlign(Paint.Align.CENTER);
            float availableWidth = getWidth()
                    - getPaddingStart()
                    - getPaddingEnd()
                    - yAxisRemained
                    - xAxisPaddingStart
                    - xAxisPaddingEnd;
            calcXAxisTextSizeAndSpacing(availableWidth);
            canvas.save();
            canvas.translate(yAxisRemained, 0);
            float startX = 0;
            onDrawHighlight(canvas);
            List<X> xAxes = this.data.getxValues();
            for (int i = 0; i < xAxes.size(); i++) {
                CharSequence source = xAxes.get(i).toReadable();
                xWidthHalf = xAxisItemWidth / 2;
                float startXFixed;
                if (axisPaint.getTextAlign() == Paint.Align.CENTER) {
                    startXFixed = startX + xWidthHalf;
                } else if (axisPaint.getTextAlign() == Paint.Align.RIGHT) {
                    startXFixed = startX + xWidthHalf * 2;
                } else {
                    startXFixed = startX;
                }
                canvas.drawText(source, 0, source.length(), startXFixed, xAxisTextCenterY, axisPaint);
                if (SHOW_DEBUG_VIEW) {
                    chartPaint.setColor(Color.parseColor("#80FF00FF"));
                    canvas.drawRect(
                            startX, viewCenterY - textHeightHalf,
                            startX + xWidthHalf, viewCenterY + textHeightHalf, chartPaint);
                    chartPaint.setColor(Color.parseColor("#80FFFF00"));
                    canvas.drawRect(
                            startX + xWidthHalf, viewCenterY - textHeightHalf,
                            startX + xWidthHalf * 2, viewCenterY + textHeightHalf, chartPaint);
                }
                startX += xAxisItemWidth + xAxisSpacing;
            }
            canvas.restore();
        }
    }

    void calcMaxMin(List<EntrySet> entrySets) {
        for (int i = 0; entrySets != null && i < entrySets.size(); i++) {
            EntrySet entrySet = entrySets.get(i);
            List<PercentChartEntry> entries = entrySet.getEntries();
            for (int j = 0; entries != null && j < entries.size(); j++) {
                PercentChartEntry entry = entries.get(j);
                if (entry != null) {
                    yAxisMax = Math.max(yAxisMax, entry.getPercent());
                    yAxisMin = Math.min(yAxisMin, entry.getPercent());
                }
            }
        }
    }

    /**
     * 根据宽度计算合适的文字大小和间距
     * @param availableWidth
     */
    void calcXAxisTextSizeAndSpacing(float availableWidth) {
        StringBuilder fullXAxisText = new StringBuilder();
        List<X> xAxes;
        if (data != null && (xAxes = data.getxValues()) != null) {
            for (int i = 0; i < xAxes.size(); i++) {
                fullXAxisText.append(xAxes.get(i).toReadable());
            }
            float fullXAxisSpacing = xAxisSpacing * (xAxes.size() - 1);
            float width = axisPaint.measureText(fullXAxisText.toString());
            if (width > availableWidth - fullXAxisSpacing) {
                axisPaint.setTextSize((xAxisTextSize = axisPaint.getTextSize() - 1));
                calcXAxisTextSizeAndSpacing(availableWidth);
            }
            xAxisItemWidth = (availableWidth - fullXAxisSpacing) / xAxes.size();
        }
    }

    private static final String TAG = "LineBarChartBase";

    protected void onDrawEntries(Canvas canvas) {
        List<EntrySet> entrySets;
        if (data != null && (entrySets = data.getData()) != null) {
            int centerY = getHeight() / 2;
            axisPaint.setTextSize(xAxisTextSize);
            axisPaint.getFontMetrics(centerMetrics);
            float xAxisTextHeight = centerMetrics.bottom - centerMetrics.top;
            axisPaint.setTextSize(yAxisTextSize);
            axisPaint.getFontMetrics(centerMetrics);
            float yAxisTextHeight = centerMetrics.bottom - centerMetrics.top;
            float availableHeight = getHeight()
                    - getPaddingTop()
                    - getPaddingBottom();
            float availableWidth = getWidth()
                    - getPaddingStart()
                    - getPaddingEnd()
                    - yAxisRemained
                    - xAxisPaddingStart
                    - xAxisPaddingEnd;
            float entrySetWidth = (availableWidth - ((entrySets.size() - 1) * xAxisSpacing)) / entrySets.size();
            float startX = yAxisRemained + xAxisPaddingStart;
            for (int i = 0; i < entrySets.size(); i++) {
                EntrySet entrySet = entrySets.get(i);
                List<PercentChartEntry> entries;
                if (entrySet != null && (entries = entrySet.getEntries()) != null) {
                    xAxisEntryWidth = (entrySetWidth - ((entries.size() - 1) * xAxisEntrySpacing)) / entries.size();
                    for (int j = 0; j < entries.size(); j++) {
                        PercentChartEntry entry = entries.get(j);
                        chartPaint.setColor(entry.getColor());
                        float baseY;
                        if (entry.getPercent() > 0) {
                            baseY = centerY - xAxisTextHeight / 2;
                            mapping.setSrc(0, yAxisMaxWithFactor);
                            mapping.setDest(baseY, yAxisTextHeight / 2);
                        } else {
                            baseY = centerY + xAxisTextHeight / 2;
                            mapping.setSrc(0, -yAxisMaxWithFactor);
                            mapping.setDest(baseY, availableHeight - yAxisTextHeight / 2);
                        }
                        float percent;
                        if (Math.abs(entry.getPercent()) > animatorPercent) {
                            percent = animatorPercent * (entry.getPercent() > 0 ? 1 : -1);
                        } else {
                            percent = entry.getPercent();
                        }
                        float entryYValue = mapping.mapValue(percent);
                        entryRect.set(
                                startX,
                                entryYValue,
                                startX + xAxisEntryWidth,
                                baseY);
                        canvas.drawRect(entryRect, chartPaint);
                        startX += entryRect.width() + xAxisEntrySpacing;
                    }
                }
                startX += xAxisSpacing - xAxisEntrySpacing;
            }
        }
    }

    public float yToPercent(float y) {
        // TODO: 2022/8/5  
        return yAxisValueMapping.mapValue(y);
    }

    public void xToIndex(float x, @NonNull int[] indexes) {
        indexes[0] = (int) (x / (xAxisItemWidth + xAxisSpacing)) - 1;
        if (indexes[0] > -1) {
            float startX = (xAxisItemWidth + xAxisSpacing) * (indexes[0] + 1);
            indexes[1] = (int) ((x - startX) / (xAxisEntryWidth + xAxisEntrySpacing));
        } else {
            indexes[1] = -1;
        }
    }

    protected void onDrawHighlight(Canvas canvas) {
        int touchEntrySetIndex = touchIndex[0];
        if (touchEntrySetIndex > -1) {
            float left = (xAxisItemWidth * touchEntrySetIndex) + (xAxisSpacing * touchEntrySetIndex);
            chartPaint.setShader(new LinearGradient(0, 0, 0, getHeight(),
                    new int[]{
                            Color.TRANSPARENT,
                            ContextCompat.getColor(getContext(), R.color.chart_highlight_color),
                            Color.TRANSPARENT
                    },
                    new float[]{0, 0.5f, 1},
                    Shader.TileMode.CLAMP));
            canvas.drawRect(left, 0, left + xAxisItemWidth, getHeight(), chartPaint);
            chartPaint.setShader(null);
            chartPaint.setColor(xAxisHighlightColor);
            axisPaint.setTextSize(xAxisTextSize);
            axisPaint.getFontMetrics(centerMetrics);
            float xAxisTextHeightHalf = (centerMetrics.bottom - centerMetrics.top) / 2;
            int viewCenterY = getHeight() >> 1;
            canvas.drawRoundRect(
                    left,
                    viewCenterY - xAxisTextHeightHalf + xAxisHighlightMargin,
                    left + xAxisItemWidth,
                    viewCenterY + xAxisTextHeightHalf - xAxisHighlightMargin,
                    xAxisHighlightRadius, xAxisHighlightRadius, chartPaint);
        }
    }
}
