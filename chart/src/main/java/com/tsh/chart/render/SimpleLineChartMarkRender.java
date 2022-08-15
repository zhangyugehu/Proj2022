package com.tsh.chart.render;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.tsh.chart.line.LineChartBase;

import java.util.List;

public class SimpleLineChartMarkRender<XAxis extends LineChartBase.XAxis, Entry extends SimpleLineChartMarkRender.AbsEntry> extends
        LineChartMarkRender<XAxis, Entry> implements LineChartBase.OnGestureListener {

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public void onMove(MotionEvent event) {

    }

    @Override
    public void onRelease(MotionEvent event) {

    }

    public abstract static class AbsEntry extends LineChartBase.Entry {

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

    }

    final float dashWidth;
    final float eventBorderWidth;
    final float eventRadius;
    int eventColor;
    float eventTextSize;
    protected DashPathEffect dashPathEffect;
    boolean isOpenEvent;
    Entry lastAnimateEntry;

    private final float[] pos = new float[2];

    public SimpleLineChartMarkRender(LineChartBase<XAxis, Entry> chart) {
        super(chart);
        dashPathEffect = new DashPathEffect(new float[] {chart.dp(2), chart.dp(2)}, 0);
        dashWidth = chart.dp(1);
        eventBorderWidth = chart.dp(1);
        eventRadius = chart.dp(7);
        eventColor = Color.RED;
        eventTextSize = chart.sp(8);
        chart.setBottomExtraHeight((eventRadius + eventBorderWidth) * 2);
        chart.addGestureListener(this);
    }

    public void setDashPathEffect(DashPathEffect dashPathEffect) {
        this.dashPathEffect = dashPathEffect;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        if (event.getY() > chart.getXAxisLineY() - eventRadius * 2 && event.getY() < chart.getXAxisLineY()) {
            List<Entry> entries = chart.transIndexToEntries(chart.transXToIndex(event.getX()));

            for (Entry entry : entries) {
                if (!TextUtils.isEmpty(entry.getEvent())) {
//                    if (entry.isShowAnimator()) {
//                        entry.hideAnimator();
//                    } else {
//                        entry.showAnimator();
//                    }
//                    if (lastAnimateEntry != null) {
//                        if (lastAnimateEntry.isHideAnimator()) {
//                            lastAnimateEntry.resetAnimator();
//                            lastAnimateEntry = null;
//                        } else {
//                            lastAnimateEntry.hideAnimator();
//                        }
//                    }

                    animateEventShow();
                    chart.clearHighlight();
                    return true;
                }
            }
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
        showAnimator = ObjectAnimator.ofFloat(this, "animatePercent", 0, 1).setDuration(1500);
        showAnimator.start();
    }

    RectF rectF = new RectF();

    @Override
    protected void onDrawEntryMark(Canvas canvas, Entry entry) {
        getEntryPos(entry, pos);
        float markX = pos[0];
        float markY = pos[1];
        if (!TextUtils.isEmpty(entry.getEvent())) {
            /// START 画事件虚线
            float markCenterY = chart.getXAxisLineY() - eventBorderWidth * 2 - eventRadius;
            markPaint.setPathEffect(dashPathEffect);
            markPaint.setColor(Color.RED);
            markPaint.setStrokeWidth(dashWidth);
            canvas.drawLine(markX, markY, markX, markCenterY - eventRadius, markPaint);
            markPaint.setPathEffect(null);
            /// END

            CharSequence detail = entry.getEventDetail();
            float detailWidth = textPaint.measureText(detail, 0, detail.length());

            /// START 画事件圆圈
            markPaint.setStyle(Paint.Style.STROKE);
            markPaint.setStrokeWidth(eventBorderWidth);
            rectF.set(markX - eventRadius,
                    markCenterY - eventRadius,
                    markX + eventRadius,
                    markCenterY + eventRadius);
            canvas.drawRoundRect(rectF, eventRadius, eventRadius, markPaint);
            /// END

            /// START 画事件简称
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setColor(eventColor);
            textPaint.setTextSize(eventTextSize);
            CharSequence eventName = entry.getEvent();
            canvas.drawText(eventName, 0, eventName.length(), markX, chart.textCenterY(markCenterY, textPaint), textPaint);
            /// END

            /// START 动画
//            if (entry.isShowAnimator() || entry.isHideAnimator()) {
//                if (entry.isShowAnimator()) {
//                    float animateWidth = detailWidth / 2 * animatePercent;
//                    rectF.set(markX - eventRadius - animateWidth,
//                            markCenterY - eventRadius,
//                            markX + eventRadius + animateWidth,
//                            markCenterY + eventRadius);
//                } else if (entry.isHideAnimator()) {
//                    float animateWidth = detailWidth / 2 * (1 - animatePercent);
//                    rectF.set(markX - eventRadius - animateWidth,
//                            markCenterY - eventRadius,
//                            markX + eventRadius + animateWidth,
//                            markCenterY + eventRadius);
//                }
//                CharSequence eventDetail = entry.getEventDetail();
//                canvas.save();
//                canvas.clipRect(rectF);
//                canvas.drawText(eventDetail, 0, eventDetail.length(), markX, chart.textCenterY(markCenterY, textPaint), textPaint);
//                canvas.restore();
//            }
            /// END
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
