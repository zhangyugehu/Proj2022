package com.tsh.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class PointTextView extends AppCompatTextView {

    final Paint paint;

    float size;
    float pointPadding;
    int color;

    public PointTextView(Context context) {
        this(context, null);
    }

    public PointTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointTextView)) {
            size = typedArray.getDimension(R.styleable.PointTextView_pointSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
            pointPadding = typedArray.getDimension(R.styleable.PointTextView_pointPadding,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
            color = typedArray.getColor(R.styleable.PointTextView_pointColor, Color.WHITE);

        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setPointPadding(float pointPadding) {
        this.pointPadding = pointPadding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(size * 2 + pointPadding, 0);
        super.onDraw(canvas);
        canvas.restore();
        paint.setColor(color);
        canvas.drawCircle(size, getHeight() >> 1, size, paint);
    }

    private static final String TAG = "PointTextView";
}
