package com.tsh.navigation;

import android.app.Application;
import android.content.Context;

import com.tsh.navigation.state.Store;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Store.Global.getStore().install(this);
    }
}
