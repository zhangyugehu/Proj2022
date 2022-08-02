package com.tsh.chart.pie;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.R;
import com.tsh.chart.data.PercentChartEntry;

import java.util.ArrayList;
import java.util.List;

public class LinePieView extends View implements IAnimatorChart {
    final Paint paint;
    final List<PercentChartEntry> entries;
    int emptyColor;
    float strokeWidth;
    float animatePercent = 1;
    long animateDuration;
    boolean isHideDetail;

    public LinePieView(Context context) {
        this(context, null);
    }

    public LinePieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinePieView)) {
            strokeWidth = typedArray.getDimension(R.styleable.LinePieView_linePieWidth,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
            animateDuration = typedArray.getInt(R.styleable.LinePieView_linePieAnimation, DEFAULT_ANIMATION);
            emptyColor = typedArray.getColor(R.styleable.LinePieView_linePieEmptyColor,
                    Color.parseColor("#111C24"));
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        entries = new ArrayList<>();
    }

    public boolean isHideDetail() {
        return isHideDetail;
    }

    public void setHideDetail(boolean hideDetail) {
        isHideDetail = hideDetail;
    }

    public void addEntry(PercentChartEntry entry) {
        entries.add(entry);
    }

    public boolean removeEntry(PercentChartEntry entry) {
        return entries.remove(entry);
    }

    public void setData(List<PercentChartEntry> data) {
        entries.clear();
        entries.addAll(data);
    }

    public List<PercentChartEntry> getData() {
        return entries;
    }

    public void setStrokeWidth(float width) {
        strokeWidth = width;
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

    public void showAnimator() {
        if (entries.isEmpty()) {
            // do nothing
        } else {
            getAnimator().start();
        }
    }

    @Override
    public Animator getAnimator() {
        ObjectAnimator animator =
            ObjectAnimator.ofFloat(this, "animatePercent", 0, 1)
                    .setDuration(animateDuration);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        return animator;
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
        float endX = getWidth() - getPaddingEnd() - getPaddingStart() - strokeWidth;
        paint.setStrokeWidth(strokeWidth);
        if (entries.isEmpty() || isHideDetail) {
            paint.setColor(emptyColor);
            canvas.drawLine(nextStart, centerY, endX, centerY, paint);
        } else for (int i = 0; i < entries.size(); i++) {
            PercentChartEntry entry = entries.get(i);
            float animateX = endX * animatePercent;
            if (animateX > nextStart) {
                float entryWidth = (availableWidth * realPercent(entry.getPercent()));
                paint.setColor(entry.getColor());
                float stopX = nextStart + entryWidth;
                canvas.drawLine(nextStart, centerY, Math.min(stopX, animateX), centerY, paint);
                nextStart = stopX + divideWidth;
            }
        }
    }

    private float realPercent(float percent) {
        float totalPercent = 0;
        for (PercentChartEntry entry : entries) {
            totalPercent += entry.getPercent();
        }
        return percent / totalPercent;
    }

    private static final String TAG = "LinePercentChart";
}
