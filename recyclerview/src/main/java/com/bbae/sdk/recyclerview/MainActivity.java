package com.bbae.sdk.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.thssh.commonlib.fragment.FragmentGetter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 pager = findViewById(R.id.pager);
        FragmentGetter getter = new FragmentGetter(3);
        pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return getter.getFragment(position);
            }

            @Override
            public int getItemCount() {
                return getter.getSize();
            }
        });
        new TabLayoutMediator(tabLayout, pager, true, (tab, position) -> tab.setText("Tab-" + position)).attach();
    }
}