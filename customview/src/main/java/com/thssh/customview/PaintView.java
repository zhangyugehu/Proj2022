package com.thssh.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.thssh.commonlib.utils.UI;
import com.thssh.customview.helper.IRepo;
import com.thssh.customview.helper.PathInfo;
import com.thssh.customview.helper.PathSaver;
import com.thssh.customview.helper.PreferencesRepo;

/**
 * TODO: document your custom view class.
 * @author hutianhang
 */
public class PaintView extends View {
    private String mExampleString;
    private int mExampleColor = Color.RED;
    private float mExampleDimension = 0;
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private Paint paint;
    private IRepo<PathInfo> repo;

    PathSaver path;
    private float mTextWidth;
    private float mTextHeight;

    public PaintView(Context context) {
        super(context);
        init(null, 0);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PaintView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.PaintView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.PaintView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.PaintView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.PaintView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.PaintView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mExampleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(UI.dp(5));

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        repo = new PreferencesRepo(getContext());

        path = new PathSaver(repo);
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    int targetPointer;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_UP:
                path.reset();
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                if (path == null) {
                    path = new PathSaver(repo);
                }
                path.moveTo(event.getX(), event.getY());
                targetPointer = event.getPointerId(event.getActionIndex());
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerId(event.getActionIndex()) == targetPointer) {
                    // 只支持单指
                    path.lineTo(event.getX(), event.getY());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                path.save();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);

        if (path != null) {
            canvas.drawPath(path.getPath(), paint);
        }
    }

}