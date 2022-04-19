package com.tsh.navigation.pages.fragments;

import androidx.navigation.fragment.NavigationFragment;

import com.thssh.commonlib.logger.L;

public class BaseFragment extends com.thssh.commonlib.activity.BaseFragment implements NavigationFragment {

    @Override
    public void willAppear() {
        L.d(getLogTag(), "willAppear");
    }

    @Override
    public void willDisappear() {
        L.d(getLogTag(), "willDisappear");
    }
}
