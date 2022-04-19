package com.tsh.navigation.pages.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.tsh.navigation.R;

public class GuideFragment extends BaseFragment {

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(R.id.btn_login).setOnClickListener(this::onLoginClick);
    }

    private void onLoginClick(View view) {
        NavOptions options = new NavOptions.Builder()
                .build();
        Bundle bundle = new Bundle();
        Navigation.findNavController(view).navigate(R.id.action_guideFragment_to_loginFragment, bundle, options);
    }

    @Override
    public void willAppear() {
        super.willAppear();
        requireActionBar().setTitle("GuideFragment");
    }
}
