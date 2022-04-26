package com.tsh.navigation.pages.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.config.RouteMapping;

import com.tsh.navigation.R;
import com.tsh.navigation.consts.Route;
import com.tsh.navigation.state.Store;
import com.tsh.navigation.state.UserState;


public class GuideFragment extends BaseFragment {

    UserState state;
    private static final int delay = 3000;
    private static final int interval = 1000;
    TextView delayLabel;

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = Store.Global.getStore().get(UserState.NAME);
    }


    private String delayText(long time) {
        return (time / 1000) + 1 + "s";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        delayLabel = setLabelText(R.id.txt_delay, delayText(delay));

        new CountDownTimer(delay, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                delayLabel.setText(delayText(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                goNextPage();
            }
        }.start();
    }

    private void goNextPage() {
        NavOptions options = new NavOptions.Builder()
//                .setPopUpTo(R.id.guideFragment, true)
                .setPopUpTo(RouteMapping.findIdByRoute(Route.GUIDE), true)
                .build();
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(state.token)) {
            Navigation.findNavController(mRootView).navigate(
                    RouteMapping.findIdByRoute(Route.LOGIN), bundle, options);
//            Navigation.findNavController(mRootView).navigate(R.id.action_guideFragment_to_nav_auth, bundle, options);
        } else {
            Navigation.findNavController(mRootView).navigate(
                    RouteMapping.findIdByRoute(Route.MAIN), bundle, options);
//            Navigation.findNavController(mRootView).navigate(R.id.action_guideFragment_to_nav_main, bundle, options);
        }
    }

    @Override
    public void willAppear() {
        super.willAppear();
        setTitle("Guide");
    }
}
