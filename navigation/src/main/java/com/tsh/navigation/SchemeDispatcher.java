package com.tsh.navigation;

import android.os.Bundle;

import androidx.navigation.DerNavGraphBuilder;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.config.NavConfig;
import androidx.navigation.config.RouteMapping;

import com.tsh.navigation.consts.Route;
import com.tsh.navigation.pages.fragments.GuideFragment;
import com.tsh.navigation.pages.fragments.LoginFragment;
import com.tsh.navigation.pages.fragments.MainFragment;
import com.tsh.navigation.pages.fragments.ProfileFragment;
import com.tsh.navigation.pages.fragments.RegisterFragment;

public class SchemeDispatcher {
    static SchemeDispatcher instance = new SchemeDispatcher();

    public static SchemeDispatcher get() {
        return instance;
    }

    final DerNavGraphBuilder graphBuilder;
    NavController navController;

    private SchemeDispatcher() {
        graphBuilder = new DerNavGraphBuilder()
                .addConfig(Route.GUIDE, NavConfig.newConfig()
                        .setStarter(true)
                        .setClassName(GuideFragment.class.getName())
                )
                .addConfig(Route.LOGIN, NavConfig.newConfig()
                        .setClassName(LoginFragment.class.getName()))
                .addConfig(Route.REGISTER, RegisterFragment.class.getName())
                .addConfig(Route.MAIN, MainFragment.class.getName())
                .addConfig(Route.PROFILE, ProfileFragment.class.getName())
            ;
    }

    public void setNavController(NavController controller) {
        navController = controller;
        graphBuilder.build(controller);
    }

    public void start(String scheme, Bundle args, int flags) {
        NavOptions options = new NavOptions.Builder()
//                .setLaunchSingleTop(flags)
                .build();
        navController.navigate(RouteMapping.findIdByRoute(scheme), args, options);
    }
}
