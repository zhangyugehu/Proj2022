package com.thssh.touchevent2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.thssh.touchevent.R;
import com.thssh.touchevent2.fragments.FragmentOne;
import com.thssh.touchevent2.fragments.FragmentThree;
import com.thssh.touchevent2.fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hutianhang
 */
public class MainActivity extends AppCompatActivity {

    interface FragmentGetter {
        Fragment invoke();
    }

    private List<FragmentGetter> mGetter;
    private ScrollView scrollView;
    private ViewPager viewpager;
    private View indicator;

    private EventHandler eventHandler = new EventHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        viewpager = findViewById(R.id.viewpager);
        scrollView = findViewById(R.id.scroll_view);
        indicator = findViewById(R.id.indicator);
        mGetter = new ArrayList<>();
        Collections.addAll(mGetter, FragmentOne::new, FragmentTwo::new, FragmentThree::new);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mGetter.get(position).invoke();
            }

            @Override
            public int getCount() {
                return mGetter.size();
            }
        });

        viewpager.post(this::fixViewPagerHeight);

        eventHandler.setScrollView(scrollView);
        eventHandler.setViewPager(viewpager);
    }

    private void fixViewPagerHeight() {
        setViewpagerHeight(scrollView.getHeight() - indicator.getHeight());
        scrollView.addOnLayoutChangeListener(this::onScrollViewLayoutChange);
    }

    private void onScrollViewLayoutChange(View view, int l, int t, int r, int b, int ol, int ot, int or, int ob) {
        setViewpagerHeight(b - t - indicator.getHeight());
    }

    private void setViewpagerHeight(int height) {
        ViewGroup.LayoutParams lp = viewpager.getLayoutParams();
        if (lp != null && lp.height != height) {
            lp.height = height;
            viewpager.setLayoutParams(lp);
        }
    }

}