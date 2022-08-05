package com.tsh.chart.percent;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.R;

public class CirclePercentView extends View implements IAnimatorChart {
    private static final long DEFAULT_ANIMATE_DURATION = 1500;

    private static final int START_ANGLE = 135;
    private static final int STOP_ANGLE = 270;

    Paint paint;
    RectF circleRectF;
    RectF labelRectF;

    float strokeWidth;

    int color;
    int activeColor;

    float percent;

    float animateProgress = STOP_ANGLE;

    long animateDuration;

    boolean hideDetail;

    public CirclePercentView(Context context) {
        this(context, null);
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentView)) {
            percent = typedArray.getFloat(R.styleable.CirclePercentView_circlePercentValue, 0);
            strokeWidth = typedArray.getDimension(R.styleable.CirclePercentView_circlePercentWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    3, getResources().getDisplayMetrics()));
            animateDuration = typedArray.getInt(R.styleable.CirclePercentView_circlePercentAnimateDuration, DEFAULT_ANIMATION);
            color = typedArray.getColor(R.styleable.CirclePercentView_circlePercentColor,
                    ContextCompat.getColor(context, R.color.chart_empty_color));
            activeColor = typedArray.getColor(R.styleable.CirclePercentView_circlePercentActiveColor,
                    ContextCompat.getColor(context, R.color.chart_active_color));
        }
        labelRectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleRectF = new RectF();
    }

    public void setHideDetail(boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public float getPercent() {
        return percent;
    }

    public void setAnimateDuration(long animateDuration) {
        this.animateDuration = animateDuration;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setActiveColor(int activeColor) {
        this.activeColor = activeColor;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        calcCircleRect();
    }

    public void setAnimateProgress(float animateProgress) {
        this.animateProgress = animateProgress;
        invalidate();
    }

    public void showAnimator() {
        if (percent > 0 && !hideDetail) {
            getAnimator().start();
        }
    }

    @Override
    public Animator getAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this,
                "animateProgress", 0, STOP_ANGLE);
        animator.setDuration((long) (animateDuration * percent));
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        return animator;
    }

    private static final String TAG = "ProgressChart";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calcCircleRect();
    }

    protected void calcCircleRect() {
        float halfStrokeWidth = strokeWidth / 2;
        circleRectF.set(getPaddingLeft() + halfStrokeWidth,
                getPaddingTop() + halfStrokeWidth,
                getWidth() - getPaddingRight() - halfStrokeWidth,
                getHeight() - getPaddingBottom() - halfStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(circleRectF, START_ANGLE, STOP_ANGLE, false, paint);
        if (!hideDetail) {
            paint.setColor(activeColor);
            canvas.drawArc(circleRectF, START_ANGLE, animateProgress * percent, false, paint);
        }
    }
}
