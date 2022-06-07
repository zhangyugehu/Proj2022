package com.thssh.touchevent3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ScrollView;

import com.thssh.touchevent.R;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        scrollView = findViewById(R.id.scroll_view);
        pager = findViewById(R.id.view_pager);
        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return PlaceholderFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
    }
}