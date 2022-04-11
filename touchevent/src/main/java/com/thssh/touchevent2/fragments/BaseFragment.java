package com.thssh.touchevent2.fragments;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.thssh.commonlib.fragment.AbsListFragment;
import com.thssh.touchevent2.EventHandler;
import com.thssh.touchevent2.views.RecyclerView2;

public abstract class BaseFragment extends AbsListFragment {
    RecyclerView2 recyclerView;

    @Override
    protected RecyclerView provideRecyclerView(Context context) {
        return (recyclerView = new RecyclerView2(context));
    }

    public void setEventHandler(EventHandler eventHandler) {
        recyclerView.setEventHandler(eventHandler);
    }
}
