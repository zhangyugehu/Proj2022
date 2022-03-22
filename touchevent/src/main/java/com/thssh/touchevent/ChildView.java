package com.thssh.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.logger.L;

/**
 * @author hutianhang
 */
public class ChildView extends androidx.appcompat.widget.AppCompatImageView {
    public ChildView(@NonNull Context context) {
        super(context);
    }

    public ChildView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handle = super.onTouchEvent(event);
        L.d("ChildView", "onTouchEvent", MotionEvent.actionToString(event.getAction()), handle);
        return handle;
    }

    private ScrollView findScrollView(ViewParent parent) {
        if (parent instanceof ScrollView) {
            return (ScrollView) parent;
        } else if (parent != null) {
            return findScrollView(parent.getParent());
        }
        return null;
    }
}
