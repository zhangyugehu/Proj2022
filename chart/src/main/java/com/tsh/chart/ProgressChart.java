package com.tsh.chart;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class ProgressChart extends View {
    Paint paint;
    RectF circleRectF;

    float strokeWidth;

    public ProgressChart(Context context) {
        this(context, null);
    }

    public ProgressChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleRectF = new RectF();
        strokeWidth = 45;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animatorShow();
    }

    float progress;
    private void animatorShow() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 270)
                .setDuration(2000);
        valueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        valueAnimator.setEvaluator(new FloatEvaluator());
        valueAnimator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            postInvalidate();
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            animatorShow();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getMode(widthMeasureSpec)),
//                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.getMode(heightMeasureSpec)));

        setMeasuredDimension(500, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float halfStrokeWidth = strokeWidth / 2;
        circleRectF.set(getPaddingLeft() + halfStrokeWidth,
                getPaddingTop() + halfStrokeWidth,
                getWidth() - getPaddingRight() - halfStrokeWidth,
                getHeight() - getPaddingBottom() - halfStrokeWidth);
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(circleRectF, -225f, 270f, false, paint);
        paint.setColor(Color.BLUE);
        canvas.drawArc(circleRectF, -225f, progress * 0.6f, false, paint);
    }
}
