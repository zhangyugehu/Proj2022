package com.tsh.changelanguageinternal;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Global.setApplicationContext(getApplicationContext());
//        registerActivityLifecycleCallbacks(new LifecycleForAppLanguage());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(AppContextWrapper.warp(base, Global.getAppLocale()));
    }
}
