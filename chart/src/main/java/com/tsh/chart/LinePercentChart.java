package com.tsh.chart;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class LinePercentChart<T extends PercentData> extends View implements IAnimatorChart {

    public interface LabelFormatter<T> {
        CharSequence format(T data);
    }

    private static final int ANIMATE_END = 1;
    private static final float DEFAULT_TEXT_ANIMATE_THRESHOLD = 0.65f;

    final Paint paint;
    final TextPaint textPaint;
    float strokeWidth;
    float labelPadding;
    T data;
    float animatePercent = ANIMATE_END;
    long animateDuration;
    /**
     * 文字开始alpha动画的阈值
     * 当{@link #animatePercent} 进行到多少是文字开始fade动画
     */
    float textAnimateThreshold = DEFAULT_TEXT_ANIMATE_THRESHOLD;

    Mapping alphaAnimateMapping;

    LabelFormatter<T> labelFormatter;

    public LinePercentChart(Context context) {
        this(context, null);
    }

    public LinePercentChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePercentChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics()));
        textPaint.setColor(Color.WHITE);

        alphaAnimateMapping = new Mapping();
        alphaAnimateMapping.setSrc(textAnimateThreshold, 1);
        alphaAnimateMapping.setDest(0, 1);
    }

    public void setLabelFormatter(LabelFormatter<T> labelFormatter) {
        this.labelFormatter = labelFormatter;
    }

    public void setLabelPadding(float labelPadding) {
        this.labelPadding = labelPadding;
    }

    public void setTextAnimateThreshold(float textAnimateThreshold) {
        this.textAnimateThreshold = textAnimateThreshold;
        alphaAnimateMapping.setSrc(textAnimateThreshold, 1);
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        paint.setStrokeWidth(strokeWidth);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setData(T data) {
        this.data = data;
        invalidate();
    }

    public void setAnimateDuration(long animateDuration) {
        this.animateDuration = animateDuration;
    }

    public void setAnimatePercent(float animatePercent) {
        this.animatePercent = animatePercent;
        invalidate();
    }

    public void animateShow() {
        if (data != null && data.getPercent() > 0) {
            ObjectAnimator animator =
                    ObjectAnimator.ofFloat(this, "animatePercent", 0, ANIMATE_END)
                    .setDuration(animateDuration);
            animator.setInterpolator(new LinearOutSlowInInterpolator());
            animator.start();
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
        return ANIMATE_END;
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
        if (data == null) {
            return;
        }
        float capSize = strokeWidth / 2;
        float centerY = getHeight() >> 1;
        float startX = getPaddingStart() + capSize;
        float textWidth = 0;
        CharSequence source = null;
        float textPadding = 0;
        if (labelFormatter != null) {
            source = labelFormatter.format(data);
            textWidth = textPaint.measureText(source.toString());
            textPadding = labelPadding + capSize;
        }
        float availableWidth = getWidth()
                - getPaddingStart()
                - getPaddingRight()
                - capSize * 2
                - textWidth
                - textPadding;
        float endX = startX + (availableWidth * data.getPercent());
        float stopX = endX * animatePercent;
        canvas.drawLine(startX, centerY, stopX, centerY, paint);
        if (source != null && animatePercent > textAnimateThreshold) {
            textPaint.setAlpha((int) ((225) * alphaAnimateMapping.mapValue(animatePercent)));
            canvas.drawText(source, 0, source.length(),
                    endX + textPadding, textCenterY(centerY, textPaint), textPaint);
        }
    }

    /**
     * 获取文字在 {@param rect} 中垂直居中 绘制时 y位置
     * @param paint
     * @return
     */
    public static float textCenterY(float centerY, Paint paint) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return centerY + (metrics.bottom - metrics.top) / 2 - metrics.bottom;
    }

}
