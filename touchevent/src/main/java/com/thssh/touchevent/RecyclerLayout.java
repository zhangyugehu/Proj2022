package com.thssh.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.thssh.commonlib.logger.L;

public class RecyclerLayout extends RecyclerView {
    public RecyclerLayout(@NonNull Context context) {
        super(context);
    }

    public RecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findScrollView(this);
        if (scrollLayout != null) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean handle;
//        if (scrollLayout != null && scrollLayout.handleEvent()) {
//            handle = false;
//        } else {
//            handle = super.onTouchEvent(e);
//        }
        handle = super.onTouchEvent(e);
        if (e.getActionMasked() == MotionEvent.ACTION_MOVE) {
            L.dLatest(
                    "RecyclerLayout",
                    "onTouchEvent",
                    MotionEvent.actionToString(e.getActionMasked()),
                    handle
            );
        } else {
            L.d(
                    "RecyclerLayout",
                    "onTouchEvent",
                    MotionEvent.actionToString(e.getActionMasked()),
                    handle
            );
        }
        return handle;
    }

    ScrollLayout scrollLayout;

    private void findScrollView(View view) {
        if (view instanceof ScrollLayout) {
            scrollLayout = (ScrollLayout) view;
        } else if (view.getParent() instanceof ViewGroup) {
            findScrollView((View) view.getParent());
        }
    }


}
