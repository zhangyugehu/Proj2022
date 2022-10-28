package com.tsh.imagecompressor;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App sInstance;
    public static App getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("App not ready yet.");
        }
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
    }

}
