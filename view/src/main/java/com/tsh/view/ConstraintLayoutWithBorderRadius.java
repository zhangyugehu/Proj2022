package com.tsh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ConstraintLayoutWithBorderRadius extends ConstraintLayout {

    final BorderRadiusDelegate delegate;

    public ConstraintLayoutWithBorderRadius(@NonNull Context context) {
        this(context, null);
    }

    public ConstraintLayoutWithBorderRadius(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConstraintLayoutWithBorderRadius(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
