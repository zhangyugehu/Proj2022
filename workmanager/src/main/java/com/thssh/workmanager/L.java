package com.thssh.workmanager;

import android.util.Log;

public class L {
    public static final String TAG = "WorkerTag";

    public static void d(String msg) {
        if (BuildConfig.DEBUG) Log.d(TAG, Thread.currentThread().getName() + ">>> " + msg);
    }
}
