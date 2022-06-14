package com.thssh.appfloatwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.thssh.commonlib.logger.L;

import java.lang.reflect.Field;

public class FloatRelativeLayout extends RelativeLayout {
    public interface IDraggable {
        void onDrag(MotionEvent event);
        void onRelease(MotionEvent event);
        void onMove(MotionEvent event);
        void onTap();
    }

    private final GestureDetector gestureDetector;

    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;

    private boolean moveInActive;

    private IDraggable draggableView;

    public FloatRelativeLayout(Context context) {
        this(context, null);
    }

    public FloatRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
//                L.d("onSingleTapUp");
                if (draggableView != null) {
                    draggableView.onTap();
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent event, float distanceX, float distanceY) {
//                L.d("onScroll", distanceX, distanceY);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                moveInActive = true;
                downX = e.getRawX();
                downY = e.getRawY();
                // 收起popView
                if (draggableView != null) {
                    draggableView.onDrag(e);
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                L.d("onFling", velocityX, velocityY);
                return false;
            }
        });
    }

    private WindowManagerMoveDelegate.OnMoveListener listener;
    public void setOnMoveListener(WindowManagerMoveDelegate.OnMoveListener listener) {
        this.listener = listener;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof IDraggable) {
            draggableView = (IDraggable) child;
        }
        super.addView(child, index, params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (moveInActive) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    if (moveInActive && listener != null) {
                        if (draggableView != null) {
                            draggableView.onMove(event);
                        }
                        float dx = event.getRawX() - downX + offsetX;
                        float dy = event.getRawY() - downY + offsetY;
                        listener.onMove(dx, dy);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // 恢复收起前的状态
                    if (draggableView != null) {
                        draggableView.onRelease(event);
                    }
                    offsetX += event.getRawX() - downX;
                    offsetY += event.getRawY() - downY;
                    moveInActive = false;
                    break;
            }
        }
        return gestureDetector.onTouchEvent(event);
    }

}
