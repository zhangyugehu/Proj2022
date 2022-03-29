package com.thssh.touchevent.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.activity.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerTwo extends RecyclerLayoutFragment {

    @Override
    protected int primaryColor() {
        return Color.parseColor("#efb680");
    }

    @Override
    protected List<String> createData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("PagerTwo-" + i);
        }
        return list;
    }
}
