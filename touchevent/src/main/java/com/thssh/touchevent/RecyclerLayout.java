package com.thssh.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.thssh.commonlib.logger.L;

/**
 * @author hutianhang
 */
public class RecyclerLayout extends RecyclerView {

    public RecyclerLayout(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findScrollView(this);
    }

    float downY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean handle;
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                boolean up = e.getY() < downY;
                if (up) {
                    scrollLayout.requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        handle = super.onTouchEvent(e);
        if (e.getActionMasked() == MotionEvent.ACTION_MOVE) {
            L.dDiff("RecyclerLayout", "onTouchEvent",
                    MotionEvent.actionToString(e.getActionMasked()), handle);
        } else {
            L.d("RecyclerLayout", "onTouchEvent",
                    MotionEvent.actionToString(e.getActionMasked()), handle);
        }
        return handle;
    }

    ScrollView scrollLayout;

    private void findScrollView(View view) {
        if (view instanceof ScrollView) {
            scrollLayout = (ScrollView) view;
        } else if (view.getParent() instanceof ViewGroup) {
            findScrollView((View) view.getParent());
        }
    }

}
