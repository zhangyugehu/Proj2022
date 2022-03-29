package com.thssh.touchevent.fragments;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.thssh.AbsListFragment;
import com.thssh.touchevent.RecyclerLayout;


public abstract class RecyclerLayoutFragment extends AbsListFragment {
    @Override
    protected RecyclerView provideRecyclerView(Context context) {
        return new RecyclerLayout(requireContext());
    }

}
