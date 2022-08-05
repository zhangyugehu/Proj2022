package com.tsh.chart;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.Nullable;

public class ViewWithKit extends View {

    protected final Paint.FontMetrics centerMetrics;

    public ViewWithKit(Context context) {
        this(context, null);
    }

    public ViewWithKit(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewWithKit(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ViewWithKit(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        centerMetrics = new Paint.FontMetrics();
    }

    protected float dp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    protected float sp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }

    public float textCenterY(RectF rect, Paint paint) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return rect.centerY() + (metrics.bottom - metrics.top) / 2 - metrics.bottom;
    }

    public float textCenterY(float centerY, Paint paint) {
        paint.getFontMetrics(centerMetrics);
        return centerY + (centerMetrics.bottom - centerMetrics.top) / 2 - centerMetrics.bottom;
    }

    private void tryDisAllowScrollParent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        while (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
            parent = parent.getParent();
        }
    }

}
