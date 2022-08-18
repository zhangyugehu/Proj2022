package com.tsh.chart.render;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;

import com.tsh.chart.R;
import com.tsh.chart.line.LineChartBase;

import java.util.List;

public class SimpleLineChartMarkRender<XAxis extends LineChartBase.XAxis, Entry extends SimpleLineChartMarkRender.AbsEntry> extends
        LineChartMarkRender<XAxis, Entry> implements LineChartBase.OnGestureListener {

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public void onMove(MotionEvent event) {
        if (prevTouchEntry != null && prevTouchEntry.getAnimatorState() == AbsEntry.STATE_OPEN) {
            prevTouchEntry.setAnimatorState(AbsEntry.STATE_CLOSE);
            animateEventShow();
        }
    }

    @Override
    public void onRelease(MotionEvent event) {

    }

    public abstract static class AbsEntry extends LineChartBase.Entry {
        private static final int STATE_NONE = 0;
        private static final int STATE_OPEN = 1;
        private static final int STATE_CLOSE = -1;

        private int animatorState;

        public AbsEntry(int index, float value) {
            super(index, value);
        }

        /**
         * 事件名称
         * @return
         */
        public abstract CharSequence getEvent();

        /**
         * 是否买入
         * @return
         */
        public abstract boolean isBuy();

        /**
         * 是否卖出
         * @return
         */
        public abstract boolean isSell();

        public abstract CharSequence getEventDetail();

        public int getAnimatorState() {
            return animatorState;
        }

        public void setAnimatorState(int animatorState) {
            this.animatorState = animatorState;
        }

    }

    final float dashWidth;
    final float eventBorderWidth;
    final float eventRadius;
    int eventColor;
    int dashColor;
    int eventBackgroundColor;
    float eventTextSize;
    protected DashPathEffect dashPathEffect;

    private final float[] pos = new float[2];

    public SimpleLineChartMarkRender(LineChartBase<XAxis, Entry> chart) {
        super(chart);
        dashPathEffect = new DashPathEffect(new float[] {chart.dp(2), chart.dp(2)}, 0);
        dashWidth = chart.dp(1);
        eventBorderWidth = chart.dp(1);
        eventRadius = chart.dp(8);
        dashColor = chart.getContext().getColor(R.color.chart_event_color);
        eventColor = chart.getContext().getColor(R.color.chart_event_color);
        eventBackgroundColor = chart.getContext().getColor(R.color.chart_background_color);
        eventTextSize = chart.sp(8);
        chart.setBottomExtraHeight(eventRadius * 2 + eventBorderWidth * 4);
        chart.addGestureListener(this);
    }

    public void setDashPathEffect(DashPathEffect dashPathEffect) {
        this.dashPathEffect = dashPathEffect;
    }
    Entry prevTouchEntry = null;
    Entry currentTouchEntry = null;

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
//        prevTouchEntry = currentTouchEntry;
//        Entry currentTouchEntry = null;
        float touchX = event.getX();
        int entryWidth = chart.getEntryWidth();
        int leftIndex = (int) (touchX / entryWidth);
        int rightIndex = leftIndex + 1;
        List<LineChartBase.EntrySet<Entry>> entrySets = chart.getData().getData();
        for (LineChartBase.EntrySet<Entry> entrySet : entrySets) {
            List<Entry> entries = entrySet.getEntries();
            int leftEntryCenterX = 0;
            int rightEntryCenterX = 0;
            if (entries != null && leftIndex < entries.size()) {
                Entry leftEntry = entries.get(leftIndex);
                if (leftEntry != null && !TextUtils.isEmpty(leftEntry.getEvent())) {
                    currentTouchEntry = leftEntry;
                    leftEntryCenterX = leftIndex * entryWidth;
                }
                Entry rightEntry = null;
                if (rightIndex < entries.size() &&
                        (rightEntry = entries.get(rightIndex)) != null &&
                        !TextUtils.isEmpty(rightEntry.getEvent())) {
                    rightEntryCenterX = rightIndex * entryWidth;
                }
                if (rightEntry != null &&
                        Math.abs(touchX - leftEntryCenterX) > Math.abs(touchX - rightEntryCenterX)) {
                    currentTouchEntry = rightEntry;
                }
            }
        }
        if (currentTouchEntry != null) {
            chart.clearHighlight();
            currentTouchEntry.setAnimatorState(AbsEntry.STATE_OPEN);
            animateEventShow();
            return true;
        } else if (prevTouchEntry != null && prevTouchEntry.getAnimatorState() == AbsEntry.STATE_OPEN) {
            prevTouchEntry.setAnimatorState(AbsEntry.STATE_CLOSE);
            animateEventShow();
            return true;
        }
        return false;
    }

    private float animatePercent;

    public void setAnimatePercent(float animatePercent) {
        this.animatePercent = animatePercent;
        chart.invalidate();
    }

    private Animator showAnimator = null;

    private void animateEventShow() {
        if (showAnimator != null) {
            showAnimator.cancel();
        }
        showAnimator = ObjectAnimator.ofFloat(this, "animatePercent", 0, 0.1f, 0.9f, 1)
                .setDuration(800);
        showAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (prevTouchEntry != null) {
                    prevTouchEntry.setAnimatorState(AbsEntry.STATE_CLOSE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (prevTouchEntry != null) {
                    prevTouchEntry.setAnimatorState(AbsEntry.STATE_NONE);
                }
                if (prevTouchEntry == currentTouchEntry) {
                    currentTouchEntry.setAnimatorState(AbsEntry.STATE_NONE);
                    prevTouchEntry = null;
                } else {
                    prevTouchEntry = currentTouchEntry;
                }
                currentTouchEntry = null;
            }
        });
        showAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        showAnimator.start();
    }

    int availableAlpha(float value) {
        return Math.min(Math.max(0, (int)(255 * value)), 255);
    }
    RectF rectF = new RectF();
    private static final String TAG = "SimpleLineChartMarkRend";

    @Override
    protected void onDrawOverEntryMark(Canvas canvas, Entry entry) {
        getEntryPos(entry, pos);
        float markX = pos[0];

        if (!TextUtils.isEmpty(entry.getEvent())) {
            /// START 画事件虚线
            float markCenterY = chart.getXAxisLineY() - eventBorderWidth * 2 - eventRadius;
            /// START 动画
            if (entry.getAnimatorState() != AbsEntry.STATE_NONE) {
                float startDrawRightX = chart.getStartDrawRight();
                CharSequence eventDetail = entry.getEventDetail();
                float detailTextWidth = textPaint.measureText(eventDetail, 0, eventDetail.length())
                        + eventRadius;
                // 展开后右侧绘画x位置
                float rightReachedX = markX
                        + detailTextWidth
                        + eventRadius
                        + eventBorderWidth;
                // 为了使展开后可显示下,x方向的偏移量
                float offsetX = 0;
                if (rightReachedX > startDrawRightX) {
                    // 当右侧会画到屏幕外时,详情往左画
                    offsetX = detailTextWidth;
                }
                float animatePercentByState =
                        entry.getAnimatorState() == AbsEntry.STATE_CLOSE
                                ? (1 - animatePercent)
                                : animatePercent;
                float animateLeftX = markX
                        - offsetX * animatePercentByState
                        - eventRadius;
                float animateRightX = animateLeftX
                        + eventRadius * 2
                        + (detailTextWidth) * animatePercentByState;
                // 防止圆角矩形比event的圆形还小 导致动画穿帮
//                animateRightX = Math.max(animateRightX, animateLeftX + eventRadius * 2);
                rectF.set(animateLeftX,
                        markCenterY - eventRadius,
                        animateRightX,
                        markCenterY + eventRadius
                );
                // 画一个不透明的背景盖住其他事件
                markPaint.setStyle(Paint.Style.FILL);
                markPaint.setColor(eventBackgroundColor);
                canvas.drawRoundRect(rectF, eventRadius, eventRadius, markPaint);
                // 画矩形框包裹文字
                markPaint.setStyle(Paint.Style.STROKE);
                markPaint.setColor(eventColor);
                canvas.drawRoundRect(rectF, eventRadius, eventRadius, markPaint);
                // 裁切画布, 让文字保持在矩形框内部
                canvas.save();
                canvas.clipRect(rectF);
                // 文字淡入淡出
                if (entry.getAnimatorState() == AbsEntry.STATE_OPEN) {
                    textPaint.setAlpha(availableAlpha(animatePercent));
                } else if (entry.getAnimatorState() == AbsEntry.STATE_CLOSE) {
                    textPaint.setAlpha(availableAlpha(1 - animatePercent));
                }
                textPaint.setTextAlign(Paint.Align.LEFT);
                float textStartX;
                if (offsetX > 0) {
                    textStartX = rectF.left;
                } else {
                    textStartX = rectF.left + eventRadius * 2;
                }
                textStartX += eventRadius / 2;
                canvas.drawText(eventDetail, 0, eventDetail.length(),
                        textStartX, chart.textCenterY(markCenterY, textPaint), textPaint);
                canvas.restore();

//                if (entry.getAnimatorState() == AbsEntry.STATE_OPEN) {
//                    if (animatePercent > 0.1) {
//                        drawEventCircle(canvas, markX, markCenterY);
//                    }
//                } else if (entry.getAnimatorState() == AbsEntry.STATE_CLOSE){
//                    if (animatePercent < 0.9) {
//                        drawEventCircle(canvas, markX, markCenterY);
//                    }
//                } else {
//                    drawEventCircle(canvas, markX, markCenterY, alpha);
//                }
                int alpha = 255;
                if (entry.getAnimatorState() == AbsEntry.STATE_OPEN) {
                    alpha = availableAlpha(animatePercent);
                } else if (entry.getAnimatorState() == AbsEntry.STATE_CLOSE) {
                    alpha = availableAlpha(1 - animatePercent);
                }
                drawEventCircle(canvas, markX, markCenterY, alpha);
                drawEventName(canvas, markX, markCenterY, entry);
            }
            /// END
        }
    }

    /**
     * 画事件圆圈
     * @param canvas
     * @param markX
     * @param markCenterY
     */
    void drawEventCircle(Canvas canvas, float markX, float markCenterY, int alpha) {
        /// START 画事件圆圈
        markPaint.setStyle(Paint.Style.STROKE);
        markPaint.setColor(eventColor);
        markPaint.setAlpha(alpha);
        markPaint.setStrokeWidth(eventBorderWidth);
        rectF.set(markX - eventRadius,
                markCenterY - eventRadius,
                markX + eventRadius,
                markCenterY + eventRadius);
        canvas.drawRoundRect(rectF, eventRadius, eventRadius, markPaint);
        /// END
    }

    /**
     * 画事件简称
     * @param canvas
     * @param markX
     * @param markCenterY
     * @param entry
     */
    void drawEventName(Canvas canvas, float markX, float markCenterY, Entry entry) {
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(eventColor);
        textPaint.setTextSize(eventTextSize);
        CharSequence eventName = entry.getEvent();
        canvas.drawText(eventName, 0, eventName.length(), markX, chart.textCenterY(markCenterY, textPaint), textPaint);
    }

    @Override
    protected void onDrawEntryMark(Canvas canvas, Entry entry) {
        getEntryPos(entry, pos);
        float markX = pos[0];
        float markY = pos[1];
        if (!TextUtils.isEmpty(entry.getEvent())) {
            /// START 画事件虚线
            float markCenterY = chart.getXAxisLineY() - eventBorderWidth * 2 - eventRadius;
            markPaint.setPathEffect(dashPathEffect);
            markPaint.setColor(dashColor);
            markPaint.setStrokeWidth(dashWidth);
            canvas.drawLine(markX, markY, markX, markCenterY - eventRadius, markPaint);
            markPaint.setPathEffect(null);
            /// END

            if (entry.getAnimatorState() == AbsEntry.STATE_NONE) {
                drawEventCircle(canvas, markX, markCenterY, 255);

//                if (entry.getAnimatorState() == AbsEntry.STATE_NONE) {
//                    textPaint.setAlpha(255);
//                } else if (entry.getAnimatorState() == AbsEntry.STATE_OPEN) {
//                    int openAlpha = availableAlpha(1 - animatePercent);
//                    textPaint.setAlpha(openAlpha);
//                    Log.i(TAG, "onDrawEntryMark: " + openAlpha);
//                } else if (entry.getAnimatorState() == AbsEntry.STATE_CLOSE) {
//                    textPaint.setAlpha(availableAlpha(animatePercent));
//                }
                drawEventName(canvas, markX, markCenterY, entry);
            }
        }
        if (entry.isBuy() || entry.isSell()) {
            /// START 画事件点
            markPaint.setStyle(Paint.Style.FILL);
            if (entry.isBuy() && entry.isSell()) {
                markPaint.setColor(Color.YELLOW);
            } else {
                markPaint.setColor(entry.isBuy() ? Color.GREEN : Color.RED);
            }
            canvas.drawCircle(markX, markY, chart.dp(2), markPaint);
            /// END
        }
    }
}
