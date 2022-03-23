package com.thssh.touchevent.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.thssh.touchevent.MainActivity;
import com.thssh.touchevent.R;
import com.thssh.touchevent.fragments.PagerOne;
import com.thssh.touchevent.fragments.PagerThree;
import com.thssh.touchevent.fragments.PagerTwo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author hutianhang
 */
public class Event1Activity extends AppCompatActivity implements View.OnTouchListener, ViewPager.OnPageChangeListener {

    ViewPager viewpager;
    ScrollView scrollview;
    View indicator;

    List<String> mTitles;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            boolean isScrollUp = event.getY() < lastY;
            if (isScrollUp && indicator.getTop() == scrollview.getScrollY()) {
                event.setAction(MotionEvent.ACTION_DOWN);
                scrollview.dispatchTouchEvent(event);
            }
        }
        lastY = event.getY();
        return false;
    }

    final List<Integer> COLORS = Arrays.asList(
            Color.parseColor("#66ccff"),
            Color.parseColor("#66ffff"),
            Color.parseColor("#66ffcc")
    );

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        indicator.setBackgroundColor(COLORS.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    interface FragmentGetter {
        Fragment invoke();
    }

    private List<FragmentGetter> mGetter;
    float lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event1);
        viewpager = findViewById(R.id.viewpager);
        scrollview = findViewById(R.id.scrollview);
        indicator = findViewById(R.id.indicator);

        mTitles = new ArrayList<>();
        mGetter = new ArrayList<>();
        Collections.addAll(mTitles, "One", "Two", "Three");
        Collections.addAll(mGetter, PagerOne::new, PagerTwo::new, PagerThree::new);
        scrollview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        scrollview.setOnTouchListener(this);
        viewpager.addOnPageChangeListener(this);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mGetter.get(position).invoke();
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });
        scrollview.post(() -> {
            ViewGroup.LayoutParams lp = viewpager.getLayoutParams();
            lp.height = scrollview.getHeight();
            viewpager.setLayoutParams(lp);
        });
    }
}