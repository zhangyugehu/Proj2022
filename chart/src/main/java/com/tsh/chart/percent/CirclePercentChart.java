package com.tsh.chart.percent;

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
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.R;

public class CirclePercentChart extends View implements IAnimatorChart {
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

    public CirclePercentChart(Context context) {
        this(context, null);
    }

    public CirclePercentChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentChart)) {
            percent = typedArray.getFloat(R.styleable.CirclePercentChart_circlePercentPercent, 0);
            strokeWidth = typedArray.getDimension(R.styleable.CirclePercentChart_circlePercentWidth,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
            animateDuration = typedArray.getInt(R.styleable.CirclePercentChart_circlePercentAnimateDuration, 1500);
            color = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentColor, Color.LTGRAY);
            activeColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentActiveColor, Color.WHITE);
        }
        labelRectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleRectF = new RectF();
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

    /**
     *
     * @param sync 是否和其他动画保持执行角度一致
     */
    public void animateShow(boolean sync) {
        if (percent > 0) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this,
                            animateProperty(), animateStart(), animateEnd());
            if (sync) {
                animator.setDuration((long) (animateDuration * percent));
            } else {
                animator.setDuration(animateDuration);
            }
            animator.setInterpolator(new LinearOutSlowInInterpolator());
            animator.start();
        }
    }


    @Override
    public String animateProperty() {
        return "animateProgress";
    }

    @Override
    public float animateStart() {
        return 0;
    }

    @Override
    public float animateEnd() {
        return STOP_ANGLE;
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
        paint.setColor(activeColor);
        canvas.drawArc(circleRectF, START_ANGLE, animateProgress * percent, false, paint);
    }
}
