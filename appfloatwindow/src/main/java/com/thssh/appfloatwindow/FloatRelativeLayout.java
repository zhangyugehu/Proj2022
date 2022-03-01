package com.thssh.appfloatwindow;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

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
    private float firstX;
    private float firstY;
    private boolean isClick;
    long lastTapTime = -1;
    OnMoveListener listener;

    interface OnMoveListener {
        void onMoved(Point point);
    }

    public void setListener(OnMoveListener listener) {
        this.listener = listener;
    }

    Point cache = new Point();

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        int action=motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                firstX = motionEvent.getRawX();
                firstY = motionEvent.getRawY();
                isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float x = motionEvent.getRawX() - firstX;
                float y = motionEvent.getRawY() - firstY;
                ViewConfiguration vc = ViewConfiguration.get(getContext());
                int min = vc.getScaledTouchSlop();
                if(x * x + y * y > min * min){
                    if (listener != null) {
                        cache.x = (int) (motionEvent.getRawX()-getMeasuredWidth()/2);
                        cache.y= (int) (motionEvent.getRawY()-getMeasuredHeight()/2 - getStatusBarHeight(getContext()));
                        listener.onMoved(cache);
                    }
                    isClick=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isClick) {
                    long curTime = System.currentTimeMillis();
                    long durTime = curTime - lastTapTime;
                    if (lastTapTime > 0 && durTime < 300) {
                        // double click
                    } else if (durTime > 1500){
                        performClick();
                    }
                    lastTapTime = curTime;
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
