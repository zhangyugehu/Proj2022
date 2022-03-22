package com.thssh.touchevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.thssh.touchevent.fragments.PagerOne;
import com.thssh.touchevent.fragments.PagerThree;
import com.thssh.touchevent.fragments.PagerTwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author hutianhang
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private List<String> mTitles;

    interface FragmentGetter {
        Fragment invoke();
    }

    private List<FragmentGetter> mGetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = findViewById(R.id.viewpager);
        mTitles = new ArrayList<>();
        mGetter = new ArrayList<>();
        Collections.addAll(mTitles, "One", "Two", "Three");
        Collections.addAll(mGetter, PagerOne::new, PagerTwo::new, PagerThree::new);
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
    }
}