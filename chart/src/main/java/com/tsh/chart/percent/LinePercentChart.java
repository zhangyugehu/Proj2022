package com.tsh.chart.percent;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.Mapping;
import com.tsh.chart.R;

import java.util.Locale;

public class LinePercentChart extends View implements IAnimatorChart {

    public interface LabelFormatter {
        CharSequence format(float percent);
    }

    private static final int ANIMATE_END = 1;
    private static final float DEFAULT_TEXT_ANIMATE_THRESHOLD = 0.80f;
    private static final LabelFormatter DEFAULT_LABEL_FORMATTER = percent ->
            String.format(Locale.US, "%.2f%%", percent * 100);

    final Paint paint;
    final TextPaint textPaint;
    float strokeWidth;
    float textPadding;
    float percent;
    float animatePercent = ANIMATE_END;
    long animateDuration;
    int color;
    int textColor;
    float textSize;
    /**
     * 文字开始alpha动画的阈值
     * 当{@link #animatePercent} 进行到多少是文字开始fade动画
     */
    float textAnimateThreshold = DEFAULT_TEXT_ANIMATE_THRESHOLD;

    Mapping alphaAnimateMapping;

    LabelFormatter labelFormatter = DEFAULT_LABEL_FORMATTER;

    public LinePercentChart(Context context) {
        this(context, null);
    }

    public LinePercentChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePercentChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinePercentChart)) {
            strokeWidth = typedArray.getDimension(R.styleable.LinePercentChart_linePercentWidth,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
            animateDuration = typedArray.getInt(R.styleable.LinePercentChart_linePercentAnimation, 1500);
            color = typedArray.getColor(R.styleable.LinePercentChart_linePercentColor, Color.DKGRAY);
            textColor = typedArray.getColor(R.styleable.LinePercentChart_linePercentTextColor, Color.WHITE);
            textSize = typedArray.getDimension(R.styleable.LinePercentChart_linePercentTextSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            textPadding = typedArray.getDimension(R.styleable.LinePercentChart_linePercentTextPadding,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            percent = typedArray.getFloat(R.styleable.LinePercentChart_linePercentValue, 0);
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);

        alphaAnimateMapping = new Mapping();
        alphaAnimateMapping.setSrc(textAnimateThreshold, 1);
        alphaAnimateMapping.setDest(0, 1);
    }

    public float getPercent() {
        return percent;
    }

    public void setLabelFormatter(LabelFormatter labelFormatter) {
        this.labelFormatter = labelFormatter;
    }

    public void setTextPadding(float textPadding) {
        this.textPadding = textPadding;
    }

    public void setTextAnimateThreshold(float textAnimateThreshold) {
        this.textAnimateThreshold = textAnimateThreshold;
        alphaAnimateMapping.setSrc(textAnimateThreshold, 1);
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public void setAnimateDuration(long animateDuration) {
        this.animateDuration = animateDuration;
    }

    public void setAnimatePercent(float animatePercent) {
        this.animatePercent = animatePercent;
        invalidate();
    }

    /**
     *
     * @param sync 是否和其他动画保持执行角度一致
     */
    public void animateShow(boolean sync) {
        if (percent > 0) {
            ObjectAnimator animator =
                    ObjectAnimator.ofFloat(this,
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
        float capSize = strokeWidth / 2;
        float centerY = getHeight() >> 1;
        float startX = getPaddingStart() + capSize;
        float textWidth;
        CharSequence source;
        float textPadding;
        source = labelFormatter.format(percent);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textWidth = textPaint.measureText(source.toString());
        textPadding = this.textPadding + capSize;
        float availableWidth = getWidth()
                - getPaddingStart()
                - getPaddingRight()
                - capSize * 2
                - textWidth
                - textPadding;
        float endX = startX + (availableWidth * percent);
        float stopX = endX * animatePercent;
        if (percent > 0) {
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(color);
            canvas.drawLine(startX, centerY, Math.max(startX, stopX), centerY, paint);
        }
        if (animatePercent > textAnimateThreshold) {
            textPaint.setAlpha((int) ((225) * alphaAnimateMapping.mapValue(animatePercent)));
            canvas.drawText(source, 0, source.length(),
                    endX + textPadding, textCenterY(centerY, textPaint), textPaint);
        }
    }

    private static final String TAG = "LinePercentChart";

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