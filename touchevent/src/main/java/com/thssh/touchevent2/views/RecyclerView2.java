package com.thssh.touchevent2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.thssh.commonlib.logger.L;
import com.thssh.touchevent2.EventHandler;

/**
 * @author hutianhang
 */
public class RecyclerView2 extends RecyclerView {

    public EventHandler eventHandler;

    public RecyclerView2(@NonNull Context context) {
        super(context);
    }

    public RecyclerView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        eventHandler.addRecyclerView(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            L.dLatest("RecyclerView2", "MOVE", event.getX(), event.getY());
        } else {
            L.d("RecyclerView2", MotionEvent.actionToString(event.getActionMasked()), event.getX(), event.getY());
        }
        return super.onTouchEvent(event);
    }
}
