package com.tsh.changelanguageinternal;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

public class LifecycleForAppLanguage implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "LifecycleForAppLanguage";

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Locale local;
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            local = configuration.getLocales().get(0);
        } else {
            local = configuration.locale;
        }
        Locale appLocale = Global.getAppLocale();
        if (local != appLocale) {
            DisplayMetrics metrics = resources.getDisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(appLocale);
                configuration.setLocales(new LocaleList(appLocale));
                activity.createConfigurationContext(configuration);
            } else {
                configuration.locale = appLocale;
            }
            resources.updateConfiguration(configuration, metrics);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
