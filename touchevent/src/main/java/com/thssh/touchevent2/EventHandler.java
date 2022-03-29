package com.thssh.touchevent2;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.viewpager.widget.ViewPager;

import com.thssh.commonlib.logger.L;

public class EventHandler {

    private ViewPager pager;
    private ScrollView scroll;

    @SuppressLint("ClickableViewAccessibility")
    public void setScrollView(ScrollView scrollView) {
        this.scroll = scrollView;
        scrollView.setOnTouchListener(new OnScrollTouchListener());
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setViewPager(ViewPager viewpager) {
        this.pager = viewpager;
        viewpager.setOnTouchListener(new OnPagerTouchListener());
    }

    class OnPagerTouchListener implements View.OnTouchListener {

        @Override
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                L.dLatest("OnPagerTouchListener", "MOVE", event.getX(), event.getY());
            } else {
                L.d("OnPagerTouchListener", MotionEvent.actionToString(event.getActionMasked()), event.getX(), event.getY());
            }
            return false;
        }
    }

    class OnScrollTouchListener implements View.OnTouchListener {

        @Override
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                L.dLatest("OnScrollTouchListener", "MOVE", event.getX(), event.getY());
            } else {
                L.d("OnScrollTouchListener", MotionEvent.actionToString(event.getActionMasked()), event.getX(), event.getY());
            }
            return false;
        }
    }
}
