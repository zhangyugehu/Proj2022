package com.tsh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * 圆角Layout代理类, 方便各类Layout实现圆角矩形功能
 *
 * Usage：
 * public class XXLayoutWithBorderRadius extends XXLayout {
 *
 *     final BorderRadiusDelegate delegate;
 *
 *     public XXLayoutWithBorderRadius(@NonNull Context context) {
 *         this(context, null);
 *     }
 *
 *     public XXLayoutWithBorderRadius(@NonNull Context context, @Nullable AttributeSet attrs) {
 *         this(context, attrs, 0);
 *     }
 *
 *     public XXLayoutWithBorderRadius(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
 *         super(context, attrs, defStyleAttr);
 *         delegate = new BorderRadiusDelegate(context, attrs, defStyleAttr, this);
 *     }
 *
 *     @Override
 *     public boolean onTouchEvent(MotionEvent event) {
 *         delegate.onTouchEvent(event);
 *         return super.onTouchEvent(event);
 *     }
 *
 *     @Override
 *     public void draw(Canvas canvas) {
 *         delegate.preDraw(canvas);
 *         super.draw(canvas);
 *     }
 * }
 *
 * @see ConstraintLayoutWithBorderRadius AND
 * @see LinearLayoutWithBorderRadius
 */
public class BorderRadiusDelegate {

    final ViewGroup target;

    private final static float DEFAULT_RADIUS_WIDTH = 1.0f;
    private final static float DEFAULT_TOUCH_OPACITY = 0.7f;

    private final Paint borderPaint;
    private final int borderWidth;
    private final int radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight;
    private final int borderColor;
    private final int borderPressedColor;
    private final RectF rectF;
    private final Path path;

    public BorderRadiusDelegate(Context context, AttributeSet attrs, int defStyle, ViewGroup target) {
        this.target = target;
        target.setWillNotDraw(false);
        target.setBackgroundColor(Color.TRANSPARENT);

        rectF = new RectF();
        path = new Path();
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderLinearLayout, 0, 0);
        int radius = typedArray.getDimensionPixelOffset(R.styleable.BorderLinearLayout_radius, 0);
        radiusTopLeft = typedArray.getDimensionPixelOffset(R.styleable.BorderLinearLayout_radiusTopLeft, radius);
        radiusTopRight = typedArray.getDimensionPixelOffset(R.styleable.BorderLinearLayout_radiusTopRight, radius);
        radiusBottomLeft = typedArray.getDimensionPixelOffset(R.styleable.BorderLinearLayout_radiusBottomLeft, radius);
        radiusBottomRight = typedArray.getDimensionPixelOffset(R.styleable.BorderLinearLayout_radiusBottomRight, radius);
        int radiusStyleValue = typedArray.getInt(R.styleable.BorderLinearLayout_borderStyle, Paint.Style.STROKE.ordinal());
        borderColor = typedArray.getColor(R.styleable.BorderLinearLayout_borderColor, Color.TRANSPARENT);
        float touchOpacity = typedArray.getFloat(R.styleable.BorderLinearLayout_touchOpacity, DEFAULT_TOUCH_OPACITY);
        borderPressedColor = typedArray.getColor(R.styleable.BorderLinearLayout_borderPressedColor, getColorWithAlpha(touchOpacity, borderColor));
        borderWidth = typedArray.getDimensionPixelOffset(R.styleable.BorderLinearLayout_borderSize, px(DEFAULT_RADIUS_WIDTH));
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.values()[radiusStyleValue]);
        typedArray.recycle();
    }

    private int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    private int px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, target.getResources().getDisplayMetrics());
    }

    public void onTouchEvent(MotionEvent event) {
        if (!target.isEnabled()) {
            // do nothing
        } else if (target.isClickable() || target.isLongClickable()) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    borderPaint.setColor(borderPressedColor);
                    target.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    borderPaint.setColor(borderColor);
                    target.invalidate();
                    break;
            }
        }
    }

    public void preDraw(Canvas canvas) {

        int offset = borderWidth == 0 ? 0 : borderWidth >> 1;
        int left = offset;
        int top = offset;
        int right = target.getWidth() - offset - offset;
        int bottom = target.getHeight() - offset - offset;

        path.reset();
        // 左上圆角
        path.moveTo(left, radiusTopLeft);
        rectF.set(left, top, left + (radiusTopLeft << 1), top + (radiusTopLeft << 1));
        path.arcTo(rectF, -180, 90);
        // 上线
        path.lineTo(right - radiusTopRight, top);
        // 右上圆角
        rectF.set(right - (radiusTopRight << 1), top, right, top + (radiusTopRight << 1));
        path.arcTo(rectF, -90, 90);
        // 右线
        path.lineTo(right, bottom - radiusBottomRight);
        // 右下圆角
        rectF.set(right - (radiusBottomRight << 1), bottom - (radiusBottomRight << 1), right, bottom);
        path.arcTo(rectF, 0, 90);
        // 下线
        path.lineTo(left + radiusBottomLeft, bottom);
        // 左下圆角
        rectF.set(left, bottom - (radiusBottomLeft << 1), left + (radiusBottomLeft << 1), bottom);
        path.arcTo(rectF, 90, 90);
        // 左线
        path.close();

        // 先画背景
        canvas.drawPath(path, borderPaint);
        // 再画child
    }
}
