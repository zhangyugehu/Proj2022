package com.thssh.touchevent.fragments;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class PagerOne extends RecyclerLayoutFragment {

    @Override
    protected int primaryColor() {
        return Color.parseColor("#d99477");
    }

    @Override
    protected List<String> createData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("PagerOne-" + i);
        }
        return list;
    }
}
