package com.thssh.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;

import androidx.viewpager.widget.ViewPager;

import com.thssh.commonlib.logger.L;

/**
 * @author hutianhang
 */
public class ScrollLayout extends ScrollView {
    public ScrollLayout(Context context) {
        this(context, null);
    }

    public ScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handle = super.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            L.dLatest("ScrollLayout", "onTouchEvent",
                    MotionEvent.actionToString(event.getAction()), handle);
            boolean isScrollUp = event.getY() < lastY;
            if (isScrollUp && isChildViewTop()) {
                L.d("ScrollLayout", "reDispatchTouchEvent");
                event.setAction(MotionEvent.ACTION_DOWN);
                dispatchTouchEvent(event);
            }
            lastY = event.getY();
        } else {
            L.d("ScrollLayout", "onTouchEvent",
                    MotionEvent.actionToString(event.getAction()), handle);
        }
        return handle;
    }

    private boolean isChildViewTop() {
        return childView != null && scrollTop == childView.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean handle = super.onInterceptTouchEvent(ev);
        boolean handle = childView != null && scrollTop < childView.getTop();
        L.dDiff("ScrollLayout", "onInterceptTouchEvent",
                MotionEvent.actionToString(ev.getAction()), handle, scrollTop, childView.getTop());
        return handle;
    }

    ViewPager viewPager;
    ChildView childView;
    int scrollTop;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findViewPager(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        L.d("onSizeChanged", w, h);
        if (viewPager != null) {
            viewPager.postDelayed(this::delayAdjustViewpagerHeight, 500);
        }
    }


    private void delayAdjustViewpagerHeight() {
        L.d("delayAdjustViewpagerHeight", getHeight());
        ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
        lp.height = getHeight();
        if (childView != null) {
            lp.height -= childView.getHeight();
        }
        viewPager.setLayoutParams(lp);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollTop = t;
//        requestDisallowInterceptTouchEvent(childView != null && scrollTop < childView.getTop());
    }

    private void findViewPager(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                viewPager = (ViewPager) child;
            } else if (child instanceof ChildView) {
                childView = (ChildView) child;
            } else if (child instanceof ViewGroup) {
                findViewPager((ViewGroup) child);
            }
        }
    }
}
