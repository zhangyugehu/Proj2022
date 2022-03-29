package com.thssh.touchevent2.fragments;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.thssh.AbsListFragment;
import com.thssh.touchevent2.views.RecyclerView2;

public abstract class BaseFragment extends AbsListFragment {
    @Override
    protected RecyclerView provideRecyclerView(Context context) {
        return new RecyclerView2(context);
    }

}
