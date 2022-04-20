package com.tsh.navigation.pages.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.tsh.navigation.R;
import com.tsh.navigation.repos.UserRepo;
import com.tsh.navigation.state.Store;
import com.tsh.navigation.state.UserState;

public class GuideFragment extends BaseFragment {

    UserState state;

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = Store.Global.getStore().get(UserState.NAME);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.postDelayed(this::goNextPage, 1000);
    }

    private void goNextPage() {
        NavOptions options = new NavOptions.Builder()
                .build();
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(state.token)) {
            Navigation.findNavController(mRootView).navigate(R.id.action_guideFragment_to_nav_auth, bundle, options);
        } else {
            Navigation.findNavController(mRootView).navigate(R.id.action_guideFragment_to_nav_main, bundle, options);
        }
    }

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Guide");
    }
}
