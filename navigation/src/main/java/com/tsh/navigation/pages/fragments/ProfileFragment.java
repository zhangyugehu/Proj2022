package com.tsh.navigation.pages.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tsh.navigation.R;
import com.tsh.navigation.state.Store;
import com.tsh.navigation.state.UserState;

public class ProfileFragment extends BaseFragment {

    UserState userState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userState = Store.Global.getStore().get(UserState.NAME);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLabelText(R.id.txt_profile, buildProfileInfo());
    }

    private String buildProfileInfo() {
        return String.format("UserName: %s\nToken: %s", userState.username, userState.token);
    }

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Profile");
    }
}
