package com.tsh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LinearLayoutWithBorderRadius extends LinearLayout {

    final BorderRadiusDelegate delegate;

    public LinearLayoutWithBorderRadius(Context context) {
        this(context, null);
    }

    public LinearLayoutWithBorderRadius(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutWithBorderRadius(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new BorderRadiusDelegate(context, attrs, defStyleAttr, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        delegate.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        delegate.preDraw(canvas);
        super.draw(canvas);
    }
}