package com.tsh.navigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.DerNavGraphBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.config.NavConfig;
import androidx.navigation.fragment.NavHostFragment;

import com.tsh.navigation.consts.Route;
import com.tsh.navigation.pages.fragments.GuideFragment;
import com.tsh.navigation.pages.fragments.LoginFragment;
import com.tsh.navigation.pages.fragments.MainFragment;
import com.tsh.navigation.pages.fragments.ProfileFragment;
import com.tsh.navigation.pages.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController;
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host);
        if (fragment instanceof NavHostFragment) {
            navController = ((NavHostFragment) fragment).getNavController();
        } else {
            navController = Navigation.findNavController(this, R.id.nav_host);
        }
        SchemeDispatcher.get().setNavController(navController);
        navController.handleDeepLink(getIntent());

    }
}