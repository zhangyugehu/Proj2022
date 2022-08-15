package com.tsh.chart.line;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.Mapping;
import com.tsh.chart.R;
import com.tsh.chart.render.Render;
import com.tsh.chart.ViewWithKit;
import com.tsh.chart.data.ChartData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * TODO: [ ] 点击mark
 * TODO: [ ] highlight 字体大小
 * @param <X>
 * @param <E>
 */
public class LineChartBase<X extends LineChartBase.XAxis, E extends LineChartBase.Entry>
        extends ViewWithKit
        implements IAnimatorChart {

    public int getEntryWidth() {
        return entryWidth;
    }

    public float getStartDrawLeft() {
        return startDrawLeft;
    }

    public float getStartDrawRight() {
        return startDrawRight;
    }

    public float transValueToY(float value) {
        return valueToYMapping.mapValue(value);
    }

    public float transYToValue(float y) {
        return valueToYMapping.remapValue(y);
    }

    public float getXAxisLineY() {
        return xAxisLineY;
    }

    public int transXToIndex(float x) {
        return (int) (x / entryWidth);
    }

    public List<E> transIndexToEntries(int index) {
        List<E> result = new ArrayList<>();
        List<EntrySet<E>> entrySets;
        if (data != null && (entrySets = data.getData()) != null) {
            for (EntrySet<E> entrySet : entrySets) {
                List<E> entries = entrySet.getEntries();
                if (index > 0 && index < entries.size()) {
                    result.add(entries.get(index));
                }
            }
        }
        return result;
    }

    //region InnerClass
    public interface XAxis {
        CharSequence getLabel();
    }

    public static class Entry extends LineEntry {
        public Entry(int index, float value) {
            super(index, value);
        }
    }

    public static class EntrySet<E extends LineEntry> extends LineEntrySet<E> {

        public EntrySet(CharSequence label, int color, List<E> entries) {
            super(label, color, entries);
        }
    }

    public interface YAxisFormatter {
        CharSequence format(float value);
    }
    //endregion

    private static final int DEFAULT_VISIBLE_COUNT = 60;
    private static final boolean USE_LINE_ANIMATOR = false;
    private static final boolean USE_XFER_ANIMATOR = true;

    //region Members
    Paint chartPaint;
    Paint highlightPaint;
    TextPaint axisPaint;
    int xAxisTextColor;
    float xAxisTextSize;
    float xAxisDashWidth;
    float xAxisSpacing;
    float[] xAxisDash;

    int yAxisLineColor;
    float yAxisLineWidth;
    float yAxisWidth;
    int yAxisTextColor;
    float yAxisTextSize;
    int yAxisCount;
    Entry yMinEntry, yMaxEntry;

    int defaultHighlightPointInnerColor;
    float defaultHighlightPointInnerRadius;
    int defaultHighlightPointOuterColor;
    float defaultHighlightPointOuterRadius;
    float xHighlightWidth;
    /**
     * 当前高亮的坐标
     */
    final PointF highlightPoint;
    /**
     * 高亮entry 位置
     */
    int highlightIndex = -1;
    /**
     * 选中高亮颜色
     */
    int xHighlightColor;
    boolean showHighlight;
    boolean showHighlightByLongPress;

    int startIndex, endIndex;
    /**
     * 底部空出空间
     */
    float bottomExtraHeight;

    /**
     * 数据源
     */
    ChartData<X, EntrySet<E>> data;
    /**
     * 可见范围内可见数量
     */
    int visibleCount;

    /**
     * 左侧偏移量
     */
    private int startDrawLeft;
    /**
     * 右侧偏移量
     */
    private int startDrawRight;
    /**
     * x坐标的y位置
     * {@link #prepare()}之后可用
     */
    private float xAxisLineY;

    /**
     * 帮助滚动的父视图
     * {@link #onMeasure(int, int)} }之后可用
     */
    HorizontalScrollView scrollViewParent;
    /**
     * 单个entry的宽度
     * {@link #prepare()}之后可用
     */
    int entryWidth = 0;
    float animatorPercent = 1;
    boolean animatorEnd = true;

    /**
     * 标记绘制类
     */
    Render<LineChartBase<X, E>> marksRender;

    /**
     * entry value 与坐标映射
     */
    final Mapping valueToYMapping;

    /**
     * 上一个点, 用于和当前点连线
     */
    final PointF lastPoint;

    /**
     * 图表总长度
     * {@link #onMeasure(int, int)} 之后确定
     */
    int contentWidth;
    int defaultIndex;

    final PorterDuffXfermode animateXfermode;
    Bitmap src, dest;
    Canvas srcCanvas, destCanvas;

    private boolean hasMeasured;


    YAxisFormatter yAxisFormatter = (value) ->
            String.format(Locale.US, "%.2f", value);
    //endregion

    public LineChartBase(Context context) {
        this(context, null);
    }

    public LineChartBase(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightGesture = new GestureDetector(context, highlightOnGestureListener);
        gestureListeners = new HashSet<>();
        xAxisDash = new float[] { 5, 10 };
        highlightPoint = new PointF();
        valueToYMapping = new Mapping();
        lastPoint = new PointF();
        animateXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        xAxisDashWidth = 1;
        xAxisSpacing = dp(10);
        xAxisTextColor = ContextCompat.getColor(context, R.color.chart_legend_text_color);

        xAxisTextSize = sp(8);
        yAxisTextColor = ContextCompat.getColor(context, R.color.chart_legend_text_color);
        yAxisLineColor = ContextCompat.getColor(context, R.color.chart_background_color);
        yAxisTextSize = sp(8);
//        yAxisWidth = dp(20);
        yAxisLineWidth = 1;
        yAxisCount = 5;

        bottomExtraHeight = dp(20);

        xHighlightColor = ContextCompat.getColor(context, R.color.chart_highlight_color);
        xHighlightWidth = 1;
        defaultHighlightPointInnerColor = ContextCompat.getColor(context, R.color.chart_highlight_color);
        defaultHighlightPointInnerRadius = dp(3);
        defaultHighlightPointOuterColor = Color.WHITE;
        defaultHighlightPointOuterRadius = dp(5);

        visibleCount = DEFAULT_VISIBLE_COUNT;

        setPadding(0, 0, 0, 0);

//        setBackgroundColor(Color.parseColor("#33FFFF00"));
    }

    public void setMarksRender(Render<LineChartBase<X, E>> marksRender) {
        this.marksRender = marksRender;
    }

    public void setData(ChartData<X, EntrySet<E>> data) {
        this.data = data;
        List<X> xValues;
        if (data != null && (xValues = data.getxValues()) != null) {
            endIndex = startIndex + Math.min(xValues.size() - startIndex, visibleCount);
        } else {
            throw new IllegalArgumentException("data or xValues notnull");
        }
        justifyPadding();
        calcMinMax();
    }

    /**
     * 调整padding 使折线能放得下
     */
    private void justifyPadding() {
        List<EntrySet<E>> dataSets;
        float maxLineStrokeWidth = 0;
        if ((dataSets = data.getData()) != null) {
            for (EntrySet<E> dataSet : dataSets) {
                maxLineStrokeWidth = Math.max(maxLineStrokeWidth, dataSet.getStrokeWidth());
            }
            setPadding((int) (getPaddingLeft() + maxLineStrokeWidth),
                    getPaddingTop(),
                    (int) (getPaddingRight() + maxLineStrokeWidth),
                    getPaddingBottom());
        }
        if (hasMeasured && scrollViewParent != null) {
            Log.i(TAG, "justifyPadding: RELAYOUT");
            scrollViewParent.requestLayout();
        }
    }

    public void setDefaultIndex(int defaultIndex) {
        this.defaultIndex = defaultIndex;
    }

    public void scrollTo(int index) {
        post(() -> scrollViewParent.scrollTo(entryWidth * index, 0));
    }

    public void smoothScrollTo(int index) {
        post(() -> scrollViewParent.smoothScrollTo(entryWidth * index, 0));
    }

    public ChartData<X, EntrySet<E>> getData() {
        return data;
    }

    public void setBottomExtraHeight(float bottomExtraHeight) {
        this.bottomExtraHeight = bottomExtraHeight;
    }

    public void setAnimatorPercent(float animatorPercent) {
        this.animatorPercent = animatorPercent;
        invalidate();
    }

    @Override
    public Animator getAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "animatorPercent", 0, 1);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                animatorEnd = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorEnd = true;
                invalidate();
            }
        });
        return animator.setDuration(DEFAULT_ANIMATION);
    }

    private void animateX() {
        if (data != null && data.getxValues() != null) {
            getAnimator().start();
        }
    }

    protected void calcMinMax() {
        List<EntrySet<E>> entrySets = data.getData();
        yMinEntry = null;
        yMaxEntry = null;
        for (int i = 0; entrySets != null && i < entrySets.size(); i++) {
            for (int entrySetIndex = 0; entrySetIndex < entrySets.size(); entrySetIndex++) {
                EntrySet<E> entrySet = entrySets.get(entrySetIndex);
                List<E> entries = entrySet.getEntries();
                for (int entryIndex = startIndex; entries != null && entryIndex < endIndex; entryIndex++) {
                    Entry entry = entries.get(entryIndex);
                    if (yMaxEntry == null || yMaxEntry.getValue() < entry.getValue()) {
                        yMaxEntry = entry;
                    }
                    if (yMinEntry == null || yMinEntry.getValue() > entry.getValue()) {
                        yMinEntry = entry;
                    }
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (scrollViewParent == null && getParent() instanceof HorizontalScrollView) {
            scrollViewParent = ((HorizontalScrollView) getParent());
            scrollViewParent.setOnScrollChangeListener(this::onScrollChanged);
        }
        List<X> xValues;
        int visibleWidth;
        if (scrollViewParent != null && data != null && (xValues = data.getxValues()) != null) {
            visibleWidth = scrollViewParent.getMeasuredWidth();
            entryWidth = visibleWidth / (visibleCount - 1);
            contentWidth = Math.max((xValues.size() - 1) * entryWidth + getPaddingStart() + getPaddingEnd(),  visibleWidth);
            setMeasuredDimension(contentWidth, getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
            startDrawRight = startDrawLeft + visibleWidth;
            scrollTo(defaultIndex);
            hasMeasured = true;
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void onScrollChanged(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        scrollX = Math.max(scrollX, 0);
        this.startDrawLeft = scrollX;
        startDrawRight = startDrawLeft + getVisibleWidth();
        List<X> xValues;
        startIndex = Math.max(transXToIndex(scrollX), 0);
        endIndex = startIndex + visibleCount;
        if (data != null && (xValues = data.getxValues()) != null) {
            endIndex = Math.min(endIndex, xValues.size());
        }
        calcMinMax();
        invalidate();
    }

    public interface OnGestureListener {
        boolean onSingleTapConfirmed(MotionEvent event);
        void onLongPress(MotionEvent event);
        void onMove(MotionEvent event);
        void onRelease(MotionEvent event);
    }

    final Set<OnGestureListener> gestureListeners;
    public void addGestureListener(OnGestureListener listener) {
        gestureListeners.add(listener);
    }

    public void removeGestureListener(OnGestureListener listener) {
        gestureListeners.remove(listener);
    }

    final GestureDetector highlightGesture;
    final GestureDetector.SimpleOnGestureListener highlightOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            boolean handled = false;
            for (OnGestureListener gestureListener : gestureListeners) {
                if (gestureListener.onSingleTapConfirmed(event)) {
                    handled = true;
                    break;
                }
            }
            if (handled) {
                return true;
            }
            if (showHighlight) {
                showHighlight = false;
                animateX();
            } else {
                showHighlight = true;
                highlightPoint.set(event.getX(), event.getY());
                highlightIndex = transXToIndex(event.getX());
            }
            invalidate();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            for (OnGestureListener gestureListener : gestureListeners) {
                gestureListener.onLongPress(event);
            }
            if (scrollViewParent != null) {
                scrollViewParent.requestDisallowInterceptTouchEvent(true);
            }
            highlightPoint.set(event.getX(), event.getY());
            highlightIndex = transXToIndex(event.getX());
            showHighlight = true;
            showHighlightByLongPress = true;
        }

    };

    public void clearHighlight() {
        showHighlight = false;
        showHighlightByLongPress = false;
        highlightIndex = -1;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        highlightGesture.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                for (OnGestureListener gestureListener : gestureListeners) {
                    gestureListener.onMove(event);
                }
                if (showHighlightByLongPress) {
                    highlightPoint.set(event.getX(), event.getY());
                    highlightIndex = transXToIndex(event.getX());
                    invalidate();
                } else if (showHighlight) {
                    clearHighlight();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                for (OnGestureListener gestureListener : gestureListeners) {
                    gestureListener.onRelease(event);
                }
                if (scrollViewParent != null) {
                    scrollViewParent.requestDisallowInterceptTouchEvent(false);
                }
                if (showHighlightByLongPress) {
                    clearHighlight();
                }
                invalidate();
                break;
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        prepare();
        onDrawBackground(canvas);
        onDrawLines(canvas);
        onDrawEntries(canvas);
        onDrawAxis(canvas);
        if (animatorEnd && marksRender != null) {
            marksRender.onDraw(canvas);
        }
        onDrawHighlight(canvas);
    }

    private void prepare() {
        axisPaint.setTextSize(xAxisTextSize);
        textHeight(axisPaint);
        float xAxisHeight = textHeight(axisPaint);
        axisPaint.setTextSize(yAxisTextSize);
        textHeight(axisPaint);

        xAxisLineY = getHeight() - yAxisLineWidth - xAxisHeight;
        // yAxis
        float firstTextBaseline = -heightMetrics.top;
        float firstLineY = firstTextBaseline + heightMetrics.bottom + yAxisLineWidth / 2;
        float lastLineY = xAxisLineY - bottomExtraHeight;
        valueToYMapping.setDest(firstLineY, lastLineY);
        if (yMaxEntry != null && yMinEntry != null) {
            valueToYMapping.setSrc(yMaxEntry.getValue(), yMinEntry.getValue());
        }
        if (USE_XFER_ANIMATOR) {
            prepareAnimatorIfNeeded();
        }
    }

    /**
     * 减少绘制时对象创建
     */
    private void prepareAnimatorIfNeeded() {
        int visibleWidth = getVisibleWidth();
        int visibleHeight = (int) (xAxisLineY - getPaddingStart());
        if (visibleWidth > 0 && visibleHeight > 0 && contentWidth > 0) {
            if (src == null || src.getWidth() != visibleWidth || src.getHeight() != visibleHeight) {
                srcCanvas = new Canvas((src =
                        Bitmap.createBitmap(visibleWidth, visibleHeight, Bitmap.Config.ARGB_8888)));
            }

            if (dest == null || dest.getWidth() != visibleWidth || dest.getHeight() != visibleHeight) {
                destCanvas = new Canvas((dest =
                        Bitmap.createBitmap(visibleWidth, visibleHeight, Bitmap.Config.ARGB_8888)));
            }
        }
    }

    private void onDrawBackground(Canvas canvas) {

    }

    private int getVisibleWidth() {
        if (scrollViewParent != null) {
            return scrollViewParent.getWidth();
        }
        return getWidth();
    }

    private static final String TAG = "LineChart";

    private void onDrawLines(Canvas canvas) {
        float startX = getPaddingStart();
        float endX = getWidth() - getPaddingStart() - getPaddingEnd();
        chartPaint.setColor(yAxisLineColor);
        chartPaint.setStrokeWidth(yAxisLineWidth);
        if (yMaxEntry != null && yMinEntry != null) {

            float lineValueStep = (yMaxEntry.getValue() - yMinEntry.getValue()) / (yAxisCount - 1);
            for (int i = 0; i < yAxisCount; i++) {
                float value = yMinEntry.getValue() + (lineValueStep * i);
                float lineY = valueToYMapping.mapValue(value);
                canvas.drawLine(startDrawLeft, lineY, startDrawLeft + endX, lineY, chartPaint);
            }
        }
        chartPaint.setPathEffect(new DashPathEffect(xAxisDash, 0));
        chartPaint.setStrokeWidth(xAxisDashWidth);
        canvas.drawLine(startX, xAxisLineY, endX, xAxisLineY, chartPaint);
        chartPaint.setPathEffect(null);
    }

    private void onDrawAxis(Canvas canvas) {
        axisPaint.setTextAlign(Paint.Align.LEFT);
        if (yMaxEntry != null && yMinEntry != null) {
            // yAxis
            axisPaint.setTextSize(yAxisTextSize);
            axisPaint.setColor(yAxisTextColor);
            float lineValueStep = (yMaxEntry.getValue() - yMinEntry.getValue()) / (yAxisCount - 1);
            for (int i = 0; i < yAxisCount; i++) {
                float value = yMinEntry.getValue() + (lineValueStep * i);
                float lineY = valueToYMapping.mapValue(value);
                CharSequence yAxisText = yAxisFormatter.format(value);
                canvas.drawText(yAxisText, 0, yAxisText.length(), startDrawLeft, lineY - heightMetrics.bottom, axisPaint);
            }
        }
        List<X> xValues;
        if (data != null && (xValues = data.getxValues()) != null) {
            // xAxis
            axisPaint.setTextSize(xAxisTextSize);
            axisPaint.setColor(xAxisTextColor);
            textHeight(axisPaint);
            float nextStartX = getPaddingStart();
            float pervDrawEndX = 0;
            float labelWidth = -1;
            for (int i = 0; i < xValues.size(); i++) {
                X xVal = xValues.get(i);
                CharSequence label = xVal.getLabel();
                if (labelWidth == -1) {
                    // FIXME 使用第一条数据作为x轴宽度标准, 每次都精准测量数据到10000级别会导致卡顿
                    labelWidth = axisPaint.measureText(label, 0, label.length());
                }
                if (nextStartX > pervDrawEndX) {
                    if (nextStartX/* + labelWidth*/ >= startDrawLeft && nextStartX + labelWidth <= startDrawRight) {
                        canvas.drawText(label, 0, label.length(),
                                nextStartX, xAxisLineY - heightMetrics.top, axisPaint);
                    }
                    pervDrawEndX = nextStartX + labelWidth + xAxisSpacing;
                }
                nextStartX += entryWidth;

            }
        }
    }

    private boolean isDrawAnimate() {
        return USE_XFER_ANIMATOR && !animatorEnd && srcCanvas != null && destCanvas != null;
    }
    private void onDrawEntries(Canvas canvas) {
        List<EntrySet<E>> entrySets;
        if (data != null && (entrySets = data.getData()) != null) {
            float startX = getPaddingStart();
            chartPaint.setStyle(Paint.Style.STROKE);

            if (isDrawAnimate()) {
                destCanvas.save();
                destCanvas.translate(-startDrawLeft, 0);
                srcCanvas.drawColor(Color.RED, PorterDuff.Mode.CLEAR);
                destCanvas.drawColor(Color.RED, PorterDuff.Mode.CLEAR);
                chartPaint.setStyle(Paint.Style.FILL);
                chartPaint.setColor(Color.RED);
                srcCanvas.drawRect(0, 0, src.getWidth() * animatorPercent, xAxisLineY, chartPaint);
            }

            for (int i = 0; i < entrySets.size(); i++) {
                EntrySet<E> entrySet = entrySets.get(i);
                if (entrySet != null) {
                    chartPaint.setColor(entrySet.getColor());
                    if (isDrawAnimate()) {
                        drawEntrySet(destCanvas, startX, entrySet);
                    } else {
                        drawEntrySet(canvas, startX, entrySet);
                    }
                }
            }

            if (isDrawAnimate()) {
                int sc = canvas.saveLayer(startDrawLeft, 0, startDrawRight, xAxisLineY, chartPaint);
                canvas.drawBitmap(dest, startDrawLeft, 0, chartPaint);
                chartPaint.setXfermode(animateXfermode);
                canvas.drawBitmap(src, startDrawLeft, 0, chartPaint);
                chartPaint.setXfermode(null);
                canvas.restoreToCount(sc);
                destCanvas.restore();
            }
        }
    }

    private void drawEntrySet(Canvas canvas, float startX, EntrySet<E> entrySet) {
        List<E> entries;
        if ((entries = entrySet.getEntries()) != null) {

            chartPaint.setColor(entrySet.getColor());
            chartPaint.setStrokeWidth(entrySet.getStrokeWidth());
            chartPaint.setStyle(Paint.Style.STROKE);
            chartPaint.setStrokeCap(Paint.Cap.ROUND);
            float nextStartX = startX;
            int count1 = 0;
            int count2 = 0;
            for (int j = 0; j < entries.size(); j++) {
                E entry = entries.get(j);
                float animatorWidth = getVisibleWidth() * animatorPercent;
                // 屏幕右边多画一段
                boolean isRightDrawEdge;
                if (USE_LINE_ANIMATOR) {
                    isRightDrawEdge = nextStartX <= startDrawLeft + animatorWidth + entryWidth;
                } else {
                    isRightDrawEdge = nextStartX <= startDrawRight + entryWidth;
                }
                if (j != 0) {
                    if (nextStartX >= startDrawLeft && isRightDrawEdge) {
                        canvas.drawLine(lastPoint.x, lastPoint.y,
                                nextStartX, valueToYMapping.mapValue(entry.getValue()),
                                chartPaint);
                    }
                }
                if (isRightDrawEdge) {
                    // 减少计算
                    count1 ++;
                    lastPoint.set(nextStartX, valueToYMapping.mapValue(entry.getValue()));
                    nextStartX += entryWidth;
                }
                count2++;
            }
//            Log.i(TAG, "drawEntrySet count1: " + count1 + ", count2: " + count2);
        }
    }

    private void onDrawHighlight(Canvas canvas) {
        if (showHighlight) {
            List<LineChartBase.EntrySet<E>> entrySets;
            if (data != null && (entrySets = data.getData()) != null) {
                highlightPaint.setTextAlign(Paint.Align.LEFT);
                DashPathEffect dashPathEffect = new DashPathEffect(xAxisDash, 0);
                int highlightX = getPaddingStart() + highlightIndex * entryWidth;
                boolean shouldDrawHighlight = false;
                for (EntrySet<E> entrySet : entrySets) {
                    List<E> entries = entrySet.getEntries();
                    int yHighlightColor;
                    if ((yHighlightColor = entrySet.getHighlightColor()) == View.NO_ID) {
                        yHighlightColor = xHighlightColor;
                    }
                    highlightPaint.setColor(entrySet.getColor());
                    highlightPaint.setStrokeWidth(entrySet.getHighlightWidth());
                    highlightPaint.setColor(yHighlightColor);
                    highlightPaint.setColor(entrySet.getColor());

                    int highlightPointOuterColor;
                    if ((highlightPointOuterColor = entrySet.getHighlightPointOuterColor()) == View.NO_ID) {
                        highlightPointOuterColor = defaultHighlightPointOuterColor;
                    }

                    float highlightPointOuterRadius;
                    if ((highlightPointOuterRadius = entrySet.getHighlightPointOuterRadius()) == -1) {
                        highlightPointOuterRadius = defaultHighlightPointOuterRadius;
                    }

                    int highlightPointInnerColor;
                    if ((highlightPointInnerColor = entrySet.getHighlightPointInnerColor()) == View.NO_ID) {
                        highlightPointInnerColor = defaultHighlightPointInnerColor;
                    }

                    float highlightPointInnerRadius;
                    if ((highlightPointInnerRadius = entrySet.getHighlightPointInnerRadius()) == -1) {
                        highlightPointInnerRadius = defaultHighlightPointInnerRadius;
                    }
                    if (entries != null && highlightIndex > -1 && entries.size() > highlightIndex) {
                        Entry entry = entries.get(highlightIndex);
                        if (entry != null) {
                            shouldDrawHighlight = true;
                            float highlightY = valueToYMapping.mapValue(entry.getValue());
                            highlightPaint.setPathEffect(dashPathEffect);
                            // xAxis highlight
                            canvas.drawLine(startDrawLeft, highlightY, startDrawRight, highlightY, highlightPaint);
                            CharSequence yText = yAxisFormatter.format(entry.getValue()) + " " + data.getxValues().get(highlightIndex).getLabel();
                            highlightPaint.setPathEffect(null);
                            textHeight(highlightPaint);
                            canvas.drawText(yText, 0, yText.length(), startDrawLeft, highlightY, highlightPaint);
                            highlightPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                            highlightPaint.setColor(highlightPointOuterColor);
                            canvas.drawCircle(highlightX, highlightY, highlightPointOuterRadius, highlightPaint);
                            highlightPaint.setColor(highlightPointInnerColor);
                            canvas.drawCircle(highlightX, highlightY, highlightPointInnerRadius, highlightPaint);
                        }
                    }
                }
                if (shouldDrawHighlight) {
                    highlightPaint.setStrokeWidth(xHighlightWidth);
                    highlightPaint.setColor(xHighlightColor);
                    highlightPaint.setPathEffect(dashPathEffect);
                    // yAxis highlight
                    canvas.drawLine(highlightX, 0, highlightX, getHeight(), highlightPaint);
                    highlightPaint.setPathEffect(null);
                }
            }
        }
    }
}
