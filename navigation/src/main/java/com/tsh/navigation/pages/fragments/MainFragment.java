package com.tsh.navigation.pages.fragments;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.HideNavHostFragment;

import com.tsh.navigation.R;
import com.tsh.navigation.consts.Route;

public class MainFragment extends BaseFragment {

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindClick(R.id.btn_profile, this::onProfileClick);
    }

    private void onProfileClick(View view) {
        HideNavHostFragment.findNavController(this).navigate(Route.PROFILE);
    }

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Main");
    }
}
