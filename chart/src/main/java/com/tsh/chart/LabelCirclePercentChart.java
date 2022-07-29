package com.tsh.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;

public class LabelCirclePercentChart extends CirclePercentChart {

    final TextPaint textPaint;
    CharSequence label;

    int labelColor;
    float labelSize;

    public LabelCirclePercentChart(Context context) {
        this(context, null);
    }

    public LabelCirclePercentChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelCirclePercentChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setLabel(CharSequence label, int color, float size) {
        setLabel(label);
        setLabelColor(color);
        setLabelSize(size);
    }

    public void setLabelSize(float labelSize) {
        this.labelSize = labelSize;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public void setLabelColor(int color) {
        this.labelColor = color;
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
        if (!TextUtils.isEmpty(label)) {
            textPaint.setColor(labelColor);
            textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, labelSize, getResources().getDisplayMetrics()));
//            paint.setAlpha(255/3);
//            paint.setStrokeWidth(2);
//            canvas.drawRect(circleRectF, paint);
//            canvas.drawRect(labelRectF, paint);
            StaticLayout staticLayout = StaticLayout.Builder.obtain(label, 0, label.length(), textPaint, (int) labelRectF.width())
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
