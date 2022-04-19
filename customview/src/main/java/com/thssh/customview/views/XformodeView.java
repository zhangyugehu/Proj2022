package com.thssh.customview.views;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class XformodeView extends View {
    public XformodeView(Context context) {
        this(context, null);
    }

    public XformodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XformodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int size(int o) {
        return (int) (o * 1.5f);
    }


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap circle = Bitmap.createBitmap(size(150), size(150), Bitmap.Config.ARGB_8888);
    Bitmap square = Bitmap.createBitmap(size(150), size(150), Bitmap.Config.ARGB_8888);

    RectF bounds = new RectF(size(150), size(50), size(300), size(200));
    RectF bounds2 = new RectF(size(150), size(50), size(300), size(200));

    PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private void init() {
        setBackgroundColor(Color.YELLOW);
        paint.setColor(Color.parseColor("#D81B60"));
        Canvas canvas = new Canvas(circle);
        canvas.drawOval(size(50), size(0), size(150), size(100), paint);

        canvas.setBitmap(square);
        paint.setColor(Color.parseColor("#2196F3"));
        canvas.drawRect(size(0), size(50), size(100), size(150), paint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        draw1(canvas);
        draw2(canvas);
    }

    void drawText(Canvas canvas) {
        int color = paint.getColor();
        paint.setColor(Color.RED);
        paint.setTextSize(100);
        canvas.drawText("SRC_IN", 0, 100, paint);
        paint.setColor(color);
    }

    void draw1(Canvas canvas) {
        int offsetX = size(250);
        int offsetY = size(0);
        bounds.set(bounds.left + offsetX, bounds.top + offsetY, bounds.right + offsetX, bounds.bottom + offsetY);
        int count = canvas.saveLayer(bounds, null);
        paint.setColor(Color.parseColor("#D81B60"));
        canvas.drawOval(offsetX + size(200), offsetY+size(50),
                offsetX+size(300), offsetY+size(150), paint);
        paint.setXfermode(mode);
        paint.setColor(Color.parseColor("#2196F3"));
        canvas.drawRect(offsetX+size(150), offsetY+size(100),
                offsetX+size(250), offsetY+size(200), paint);
        paint.setXfermode(null);
        canvas.restoreToCount(count);
    }

    void draw2(Canvas canvas) {
        int count = canvas.saveLayer(bounds2, null);
        canvas.drawBitmap(circle, size(150), size(50), paint);
        paint.setXfermode(mode);
        canvas.drawBitmap(square, size(150), size(50), paint);
        paint.setXfermode(null);
        canvas.restoreToCount(count);
    }
}
