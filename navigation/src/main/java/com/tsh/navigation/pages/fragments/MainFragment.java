package com.tsh.navigation.pages.fragments;


import com.tsh.navigation.R;

public class MainFragment extends BaseFragment {

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Main");
    }
}
