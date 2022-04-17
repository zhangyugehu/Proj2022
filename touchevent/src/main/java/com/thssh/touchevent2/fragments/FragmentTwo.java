package com.thssh.touchevent2.fragments;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends BaseFragment {
    @Override
    protected int primaryColor() {
        return Color.parseColor("#f0d689");
    }

    @Override
    protected List<String> createData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("FragmentTwo-" + i);
        }
        return list;
    }
}
