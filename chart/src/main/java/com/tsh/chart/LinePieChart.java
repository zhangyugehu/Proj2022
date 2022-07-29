package com.tsh.chart;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import java.util.ArrayList;
import java.util.List;

public class LinePieChart extends View implements IAnimatorChart {

    public static class Entry {
        float percent;
        int color;

        public Entry() {
        }

        public Entry(float percent, int color) {
            this.percent = percent;
            this.color = color;
        }
    }

    final Paint paint;
    final List<Entry> entries;
    int emptyColor;
    float strokeWidth;
    float animatePercent = 1;
    long animateDuration;

    public LinePieChart(Context context) {
        this(context, null);
    }

    public LinePieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        entries = new ArrayList<>();
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
        invalidate();
    }

    public void removeEntry(Entry entry) {
        if (entries.remove(entry)) {
            invalidate();
        }
    }

    public void setData(List<Entry> data) {
        entries.clear();
        entries.addAll(data);
    }

    public List<Entry> getData() {
        return entries;
    }

    public void setStrokeWidth(float width) {
        paint.setStrokeWidth(width);
        strokeWidth = width;
        invalidate();
    }

    public void setEmptyColor(int emptyColor) {
        this.emptyColor = emptyColor;
    }

    public void setAnimateDuration(long animateDuration) {
        this.animateDuration = animateDuration;
    }

    public void setAnimatePercent(float animatePercent) {
        this.animatePercent = animatePercent;
        invalidate();
    }

    public void animateShow() {
        if (entries.isEmpty()) {
            // do nothing
        } if (isShown()) {
            ObjectAnimator animator =
                    ObjectAnimator.ofFloat(this, animateProperty(), animateStart(), animateEnd())
                    .setDuration(animateDuration);
            animator.setInterpolator(new LinearOutSlowInInterpolator());
            animator.start();
        } else {
            post(this::animateShow);
        }
    }

    @Override
    public String animateProperty() {
        return "animatePercent";
    }

    @Override
    public float animateStart() {
        return 0;
    }

    @Override
    public float animateEnd() {
        return 1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            animateShow();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            int paddingVertical = getPaddingTop() + getPaddingBottom();
            height = (int) (paddingVertical + strokeWidth * 2);
        } else {
            height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        }
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerY = getHeight() >> 1;
        float capWidth = strokeWidth / 2;
        float userDivideWidth = 10;
        float divideWidth = capWidth * 2 + userDivideWidth;
        float availableWidth = getWidth()
                - /*最左边笔帽*/capWidth
                - getPaddingLeft()
                - getPaddingRight()
                - /*中间笔帽+间隔*/(divideWidth * (entries.size() - 1))
                - /*最右边笔帽*/capWidth;
        float nextStart = capWidth + getPaddingStart();
        float endX = getRight() - getPaddingEnd() - (strokeWidth / 2);
        if (entries.isEmpty()) {
            paint.setColor(emptyColor);
            canvas.drawLine(nextStart, centerY, endX, centerY, paint);
        } for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            float animateX = endX * animatePercent;
            if (animateX > nextStart) {
                float entryWidth = (availableWidth * realPercent(entry.percent));
                paint.setColor(entry.color);
                float stopX = nextStart + entryWidth;
                canvas.drawLine(nextStart, centerY, Math.min(stopX, animateX), centerY, paint);
                nextStart = stopX + divideWidth;
            }
        }
    }

    private float realPercent(float percent) {
        float totalPercent = 0;
        for (Entry entry : entries) {
            totalPercent += entry.percent;
        }
        return percent / totalPercent;
    }

    private static final String TAG = "LinePercentChart";
}
