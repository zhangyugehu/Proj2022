package com.tsh.navigation.pages.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.navigation.config.RouteMapping;
import androidx.navigation.fragment.HideNavHostFragment;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.simple.SimpleTextWatcher;
import com.thssh.commonlib.utils.SafeObjects;
import com.thssh.commonlib.views.LoadingWrapper;
import com.tsh.navigation.R;
import com.tsh.navigation.consts.Route;
import com.tsh.navigation.state.AuthState;
import com.tsh.navigation.state.Store;
import com.tsh.navigation.state.UserState;

import java.util.UUID;

public class LoginFragment extends BaseFragment {

    AuthState authState;
    UserState userState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authState = Store.Global.getStore().get(AuthState.NAME);
        userState = Store.Global.getStore().get(UserState.NAME);
        authState.<AuthState>listen(state -> {
            L.d("UserState", state);
        });
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Login");
        setLabelText(R.id.et_username, authState.username);
        setLabelText(R.id.et_password, authState.password);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindClick(R.id.btn_register, this::onLoginClick);
        bindClick(R.id.btn_login, this::onRegisterClick);
        addTextWatch(R.id.et_username, new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                authState.<AuthState>setState(state -> {
                    state.username = s.toString();
                });
            }
        });
        addTextWatch(R.id.et_password, new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                authState.<AuthState>setState(state -> {
                    state.password = s.toString();
                });
            }
        });
    }

    private void onRegisterClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", authState.username);
        bundle.putString("password", authState.password);
//        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment, bundle);
        Navigation.findNavController(view).navigate(RouteMapping.findIdByRoute(Route.REGISTER), bundle);
    }

    private void onLoginClick(View view) {
        if (SafeObjects.isNotPass(TextUtils::isEmpty, authState.username, authState.password)) {
            Toast.makeText(requireContext(), "username/password empty!", Toast.LENGTH_LONG).show();
            return;
        }
        LoadingWrapper.with(view).show();
        new CountDownTimer(1500, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                userState.<UserState>setState(state -> state.token = UUID.randomUUID().toString());
                HideNavHostFragment.findNavController(LoginFragment.this).navigate(Route.MAIN);
                LoadingWrapper.with(view).dismiss();
            }
        }.start();
    }
}
