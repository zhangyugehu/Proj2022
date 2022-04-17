package com.thssh.commonlib.executor;

import android.os.Looper;

public class ThreadChecker {
    public static boolean isUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
