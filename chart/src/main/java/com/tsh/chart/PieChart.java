package com.tsh.chart;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends View implements IAnimatorChart {
    /**
     * 饼图实体
     */
    public static class Entry {
        /**
         * 占比
         */
        @FloatRange(from = 0, to = 1)
        public float percent;
        /**
         * 颜色
         */
        public int color;
        /**
         * 是否是主图
         */
        public boolean isPrimary;

        public Entry() {
        }

        public Entry(float percent, int color, boolean isPrimary) {
            this.percent = percent;
            this.color = color;
            this.isPrimary = isPrimary;
        }
    }

    /**
     * 开始绘画角度
     */
    private static final int DRAW_START_ANGLE = -90;
    /**
     * 默认分割角度
     */
    private static final float DEFAULT_DIVIDE_ANGLE = 2.0f;
    /**
     * 默认膨胀因子
     */
    private static final float DEFAULT_EXPANSION_FACTOR = 1.7f;

    final List<Entry> entries;
    final Paint chartPaint;
    final RectF circleRectF;

    /**
     * 默认画笔宽度
     */
    private float strokeWidth;

    /**
     * {@link Entry#isPrimary} 膨胀系数
     */
    private float expansionFactor = DEFAULT_EXPANSION_FACTOR;

    /**
     * 饼图间间隔角度
     */
    private float divideAngle = DEFAULT_DIVIDE_ANGLE;

    /**
     * 动画执行角度
     */
    @FloatRange(from = 0, to = 360)
    private float animateAngle = 360;

    /**
     * 动画执行时长
     */
    private long animateDuration = 1500;

    /**
     * 没有数据 默认圆环颜色
     */
    private int emptyColor = Color.LTGRAY;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chartPaint.setStyle(Paint.Style.STROKE);
        chartPaint.setStrokeCap(Paint.Cap.ROUND);
        circleRectF = new RectF();
        entries = new ArrayList<>();
        strokeWidth = 20;
    }

    /**
     * 设置主图膨胀系数
     * @see Entry#isPrimary
     *
     * @param expansionFactor
     */
    public void setExpansionFactor(float expansionFactor) {
        this.expansionFactor = expansionFactor;
        postInvalidate();
    }

    /**
     * 分割线角度
     * @param divideAngle
     */
    public void setDivideAngle(float divideAngle) {
        this.divideAngle = divideAngle;
        postInvalidate();
    }

    /**
     * 动画时长
     * @param animateDuration
     */
    public void setAnimateDuration(long animateDuration) {
        this.animateDuration = animateDuration;
    }

    /**
     * 设置默认画笔宽度
     * @param strokeWidth
     */
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        postInvalidate();
    }

    /**
     * 没有数据时的颜色
     * @param emptyColor
     */
    public void setEmptyColor(int emptyColor) {
        this.emptyColor = emptyColor;
        postInvalidate();
    }

    /**
     * 添加饼图数据
     * @param entry
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
        postInvalidate();
    }

    /**
     * 移除饼图数据
     * @param entry
     */
    public void removeEntry(Entry entry) {
        if (entries.remove(entry)) {
            postInvalidate();
        }
    }

    public void setData(@NonNull List<Entry> entries) {
        this.entries.clear();
        this.entries.addAll(entries);
        invalidate();
    }

    public List<Entry> getData() {
        return entries;
    }

    public void setAnimateAngle(@FloatRange(from = 0, to = 360) float animateAngle) {
        this.animateAngle = animateAngle;
        invalidate();
    }

    /**
     * 动画显示
     */
    public void animateShow() {
        if (!entries.isEmpty()) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this,
                            animateProperty(), animateStart(), animateEnd())
                            .setDuration(animateDuration);
            animator.setInterpolator(new LinearOutSlowInInterpolator());
            animator.start();
        }
    }

    @Override
    public String animateProperty() {
        return "animateAngle";
    }

    @Override
    public float animateStart() {
        return 0;
    }

    @Override
    public float animateEnd() {
        return 360;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            animateShow();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float paddingToComfortable = strokeWidth / 2 * Math.max(expansionFactor, 1);
        circleRectF.set(
                getPaddingStart() + paddingToComfortable,
                getPaddingTop() + paddingToComfortable,
                getWidth() - getPaddingEnd() - paddingToComfortable,
                getHeight() - getPaddingBottom() - paddingToComfortable
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (entries.isEmpty()) {
            chartPaint.setStrokeWidth(strokeWidth);
            chartPaint.setColor(emptyColor);
            canvas.drawArc(circleRectF, 0, 360, false, chartPaint);
            // finish draw
            return;
        }
        // 下次绘画起点角度
        float nextStartAngle = DRAW_START_ANGLE;
        // 周长
        float perimeter = (float) (2 * Math.PI * circleRectF.width() / 2);
        // 每一份长度对应的角度
        float lengthAngle = 360 / perimeter;
        // 笔帽占用角度
        float capAngle = lengthAngle * strokeWidth / 2;
        // 真正分割的角度(算上笔帽占用的角度)
        float divideRealisticAngle = capAngle * 2 + divideAngle;
        // 剩余可用于展示的角度
        float availableAngle = (360 - (divideRealisticAngle * entries.size()));
        // 消费动画角度animatorAngle
        float consumedAngle = 0;
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            if (animateAngle > consumedAngle) {
                chartPaint.setColor(entry.color);
                if (entry.isPrimary) {
                    // 设置主图画笔宽度
                    chartPaint.setStrokeWidth(strokeWidth * expansionFactor);
                } else {
                    chartPaint.setStrokeWidth(strokeWidth);
                }
                float entryAngle = calcPercent(entry.percent) * availableAngle;
                // 由于放大而膨胀的角度
                float expansionCapAngle = 0;
                if (entry.isPrimary) {
                    expansionCapAngle = capAngle * (expansionFactor - 1);
                }
                float startAngle = nextStartAngle + expansionCapAngle;
                float sweepAngle = Math.min(
                        /*当前部分总共绘制的角度(减去变大后多出来的笔帽)*/entryAngle - expansionCapAngle * 2,
                        /*当前部分动画执行角度*/animateAngle - consumedAngle);
//                float sweepAngle = animateAngle - consumedAngle;
                canvas.drawArc(circleRectF, startAngle, sweepAngle, false, chartPaint);
                // 设置下次绘画起点角度
                nextStartAngle += entryAngle + divideRealisticAngle;
            }
            consumedAngle += calcPercent(entry.percent) * availableAngle;
        }
    }

    /**
     * 使用所有entry的percent总和计算percent
     * @param percent
     * @return
     */
    private float calcPercent(float percent) {
        float total = 0f;
        for (Entry entry : entries) {
            total += entry.percent;
        }
        return percent / total;
    }

    private static final String TAG = "PieChart";
}
