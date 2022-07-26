package com.tsh.chart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class PieChart extends View {
    Paint paint;
    RectF circleRectF;

    float strokeWidth;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleRectF = new RectF();
        strokeWidth = 45;
    }

    float progress = 0;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animateShow();
    }

    private void animateShow() {
        ValueAnimator valueAnimator = ValueAnimator
                .ofFloat(0, 360)
                .setDuration(3000);
        valueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            Object value = animation.getAnimatedValue();
            progress = (float) value;
            postInvalidate();
        });
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getMode(widthMeasureSpec)),
//                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.getMode(heightMeasureSpec)));

        setMeasuredDimension(500, 500);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            animateShow();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float halfStrokeWidth = strokeWidth / 2;
        circleRectF.set(getPaddingLeft() + halfStrokeWidth,
                getPaddingTop() + halfStrokeWidth,
                getWidth() - getPaddingRight() - halfStrokeWidth,
                getHeight() - getPaddingBottom() - halfStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(strokeWidth/2);
        paint.setColor(Color.RED);
        float start = -90;
        float current = start;
        float consumed;
        paint.setStrokeWidth(strokeWidth);
        if (progress > 0) {
            canvas.drawArc(circleRectF, current, Math.min(45, progress), false, paint);
        }

        paint.setStrokeWidth(strokeWidth / 2);
        if (progress > (consumed = 45)) {
            paint.setColor(Color.LTGRAY);
            canvas.drawArc(circleRectF, (current += (45f + strokeWidth / 4)), Math.min(60, progress-consumed), false, paint);
        }

        if (progress > (consumed = 45 + 60)) {
            paint.setColor(Color.BLUE);
            canvas.drawArc(circleRectF, (current += (60f + strokeWidth / 4)), Math.min(90, progress-consumed), false, paint);
        }

        if (progress > (consumed = 45 + 60 + 90)) {
            paint.setColor(Color.GRAY);
            canvas.drawArc(circleRectF, (current += (90f + strokeWidth / 4)), Math.min(60, progress-consumed), false, paint);
        }

        if (progress > (consumed = 45 + 60 + 90 + 60)) {
            paint.setColor(Color.GREEN);
            current += (60f + strokeWidth / 4);
            float lastSweep = (360 + start - current - strokeWidth / 4);
            canvas.drawArc(circleRectF, current, Math.min(lastSweep, progress-consumed), false, paint);
        }


    }

    private static final String TAG = "PieChart";
}
