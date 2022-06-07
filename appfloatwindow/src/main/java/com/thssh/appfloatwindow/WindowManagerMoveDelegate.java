package com.thssh.appfloatwindow;

import android.view.MotionEvent;

public class WindowManagerMoveDelegate {
    public interface OnMoveListener {
        void onMove(float dx, float dy);
    }

    final OnMoveListener listener;

    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;

    public WindowManagerMoveDelegate(OnMoveListener listener) {
        this.listener = listener;
    }

    void onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getRawX() - downX + offsetX;
                float dy = event.getRawY() - downY + offsetY;
                if (listener != null) {
                    listener.onMove(dx, dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                offsetX += event.getRawX() - downX;
                offsetY += event.getRawY() - downY;
                break;
        }
    }
}
