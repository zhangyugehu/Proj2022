package com.thssh;

import android.graphics.PointF;
import android.view.MotionEvent;

public class EventEvent {

    PointF down = new PointF();
    PointF move = new PointF();

    public void onEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                down.x = event.getX();
                down.y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                move.x = event.getX();
                move.y = event.getY();
                break;
            default: break;
        }
    }

    public boolean v() {
        return Math.abs(move.x - down.x) > Math.abs(move.y - down.y);
    }

    public boolean h() {
        return Math.abs(move.y - down.y) > Math.abs(move.x - down.x);
    }

    public boolean up() {
        return h() && move.y < down.y;
    }

    public boolean down() {
        return h() && move.y > down.y;
    }

    public boolean left() {
        return v() && move.x > down.x;
    }

    public boolean right() {
        return v() && move.x < down.x;
    }
}
