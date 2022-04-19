package com.tsh.navigation.pages.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.simple.SimpleTextWatcher;
import com.tsh.navigation.R;
import com.tsh.navigation.state.UserState;

public class LoginFragment extends BaseFragment {

    UserState userState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userState = new UserState();
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void willAppear() {
        setTitle("LoginFragment");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText userNameInput, passwordInput;
        (userNameInput = findViewById(R.id.et_username)).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                userState.<UserState>setState(state -> {
                    state.username = s.toString();
                });
            }
        });
        (passwordInput = findViewById(R.id.et_password)).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                userState.<UserState>setState(state -> {
                    state.password = s.toString();
                });
            }
        });
    }
}
