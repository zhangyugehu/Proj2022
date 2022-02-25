package com.thssh.commonlib.logger;

import android.util.Log;

public class L {
    private static final String TAG = "Proj2022";

    public static void d(Object... msgQueue) {
        StringBuilder sb = new StringBuilder();
        for (Object msg : msgQueue) {
            if (msg != null) sb.append(msg.toString()).append(" ");
        }
        Log.d(TAG, sb.toString());
    }

    public static void td(Object... msgQueue) {
        StringBuilder sb = new StringBuilder();
        for (Object msg : msgQueue) {
            if (msg != null) sb.append(msg).append(" ");
        }
        Log.d(TAG, tNamePrefix() + sb);
    }

    private static String tNamePrefix() {
        return Thread.currentThread().getName() + "]";
    }
}
