package com.tsh.navigation.pages.fragments;

import com.tsh.navigation.R;

public class ProfileFragment extends BaseFragment {

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Profile");
    }
}
