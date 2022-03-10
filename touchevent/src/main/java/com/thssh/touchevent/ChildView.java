package com.thssh.touchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.thssh.commonlib.logger.L;

/**
 * @author hutianhang
 */
public class ChildView extends View {
    private final Paint paint;
    public ChildView(Context context) {
        this(context, null);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#a9af90"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        L.d("ChildView", "onMeasure", width, height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        L.d("ChildView", "onDraw");
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
