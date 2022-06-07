package com.thssh.appfloatwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.thssh.commonlib.logger.L;

import java.lang.reflect.Field;

public class FloatRelativeLayout extends RelativeLayout {
    public FloatRelativeLayout(Context context) {
        super(context);
    }

    public FloatRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setBackgroundColor(Color.parseColor("#80F0F0F0"));
    }
    private boolean isClick;
    long lastDownTime = -1;
    WindowManagerMoveDelegate moveDelegate;

    public void setOnMoveListener(WindowManagerMoveDelegate.OnMoveListener listener) {
        moveDelegate = new WindowManagerMoveDelegate(listener);
    }

    Point cache = new Point();
    ViewConfiguration vc = ViewConfiguration.get(getContext());
    int min = -1;

    private int getTouchSlop() {
        if (min == -1) {
            min = vc.getScaledTouchSlop();
        }
        return min;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (moveDelegate != null) {
            moveDelegate.onTouchEvent(event);
        }
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastDownTime = System.currentTimeMillis();
                isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                isClick=false;
                break;
            case MotionEvent.ACTION_UP:
                if (isClick) {
                    long curTime = System.currentTimeMillis();
                    if (curTime - lastDownTime > 1_500) {
                        L.d("performLongClick");
                        performLongClick();
                    } else {
                        L.d("performClick");
                        performClick();
                    }
                }
                break;
        }
        return false;
    }


    private static int STATUS_BAR_HEIGHT;
    /**
     * 状态栏高度
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        if (STATUS_BAR_HEIGHT != 0) {
            return STATUS_BAR_HEIGHT;
        }
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelOffset(resId);
        }
        if (STATUS_BAR_HEIGHT <= 0) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return STATUS_BAR_HEIGHT;
    }
}
