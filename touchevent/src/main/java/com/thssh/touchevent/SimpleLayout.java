package com.thssh.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.logger.L;

/**
 * @author hutianhang
 */
public class SimpleLayout extends FrameLayout {
    public SimpleLayout(@NonNull Context context) {
        super(context);
    }

    public SimpleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handle = super.onTouchEvent(event);
        L.d("SimpleLayout", "onTouchEvent", MotionEvent.actionToString(event.getAction()), handle);
        return handle;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean handle = super.onInterceptHoverEvent(event);
        L.d("SimpleLayout", "onInterceptHoverEvent", MotionEvent.actionToString(event.getAction()), handle);
        return handle;
    }
}
