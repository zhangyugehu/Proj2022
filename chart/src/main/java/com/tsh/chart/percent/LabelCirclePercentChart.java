package com.tsh.chart.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.tsh.chart.R;

public class LabelCirclePercentChart extends CirclePercentChart {
    private static final boolean DEBUG_SHOW_LIMIT = false;

    final TextPaint textPaint;
    CharSequence text;

    int textColor;
    float textSize;

    public LabelCirclePercentChart(Context context) {
        this(context, null);
    }

    public LabelCirclePercentChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelCirclePercentChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelCirclePercentChart)) {
            textSize = typedArray.getDimension(R.styleable.LabelCirclePercentChart_circlePercentTextSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            text = typedArray.getString(R.styleable.LabelCirclePercentChart_circlePercentText);
            textColor = typedArray.getColor(R.styleable.LabelCirclePercentChart_circlePercentTextColor, Color.LTGRAY);
            if (typedArray.hasValue(R.styleable.LabelCirclePercentChart_circlePercentTypeface)) {
                int fontId = typedArray.getResourceId(R.styleable.LabelCirclePercentChart_circlePercentTypeface, -1);
                setTypeface(ResourcesCompat.getFont(context, fontId));
            }
        }
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setText(CharSequence label, int color, float size) {
        setText(label);
        setTextColor(color);
        setTextSize(size);
    }

    public void setTextSize(float size) {
        this.textSize = size;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void setTypeface(Typeface typeface) {
        textPaint.setTypeface(typeface);
        invalidate();
    }

    @Override
    protected void calcCircleRect() {
        super.calcCircleRect();
        // 根据三角函数算出圆内切正方形的变长 取一半
        double insertRectSideHalf = Math.sqrt(Math.pow(circleRectF.width(), 2) / 2) / 2;
        float strokeWidthHalf = strokeWidth / 2;

        labelRectF.set(
                (float) (circleRectF.centerX() - insertRectSideHalf + strokeWidthHalf),
                (float) (circleRectF.centerY() - insertRectSideHalf + strokeWidthHalf),
                (float) (circleRectF.centerX() + insertRectSideHalf - strokeWidthHalf),
                (float) (circleRectF.centerY() + insertRectSideHalf - strokeWidthHalf)
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(text)) {
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            if (DEBUG_SHOW_LIMIT) {
                paint.setAlpha(255 / 3);
                paint.setStrokeWidth(2);
                canvas.drawRect(circleRectF, paint);
                canvas.drawRect(labelRectF, paint);
            }
            StaticLayout staticLayout = StaticLayout.Builder.obtain(text, 0, text.length(), textPaint, (int) labelRectF.width())
                    .setMaxLines(2)
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setEllipsize(TextUtils.TruncateAt.END)
                    .build();
            canvas.save();
            canvas.translate(
                    (-staticLayout.getWidth() >> 1) + labelRectF.centerX(),
                    (-staticLayout.getHeight() >> 1) + labelRectF.centerY());
            staticLayout.draw(canvas);
            canvas.restore();
        }
    }
}
