package com.tsh.navigation.pages.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.simple.SimpleTextWatcher;
import com.tsh.navigation.R;
import com.tsh.navigation.state.AuthState;
import com.tsh.navigation.state.Store;

public class RegisterFragment extends BaseFragment {

    AuthState authState;

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Register");
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_register;
    }

    String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authState = Store.Global.getStore().get(AuthState.NAME);
        Bundle args = getArguments();
        if (args != null) {
            L.d((username = args.getString("username")));
        } else {
            L.d("getArguments null");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLabelText(R.id.et_username, username);
        setLabelText(R.id.et_password, authState.password);
        addTextWatch(R.id.et_username, new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                authState.<AuthState>setState(state -> state.username = s.toString());
            }
        });
        addTextWatch(R.id.et_password, new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                authState.<AuthState>setState(state -> state.password = s.toString());
            }
        });
    }
}
