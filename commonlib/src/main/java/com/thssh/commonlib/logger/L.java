package com.thssh.commonlib.logger;

import android.text.TextUtils;
import android.util.Log;

import java.util.Map;

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

    public static String getStackTracesPlain(int depth) {
        Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
        StringBuilder sb = new StringBuilder();
        boolean startRecord = false;
        int depthIdx = 0;
        for (Map.Entry<Thread, StackTraceElement[]> entry: traces.entrySet()){
            if (Thread.currentThread() == entry.getKey()) {
                for (StackTraceElement ele : entry.getValue()) {
                    if (startRecord) {
                        if (++depthIdx > depth) break;
                        sb.append("\n👉")
                                .append(ele.getClassName()).append(".").append(ele.getMethodName())
                                .append("@Line: ").append(ele.getLineNumber())
                                .append("👈");
                    }
                    if (TextUtils.equals(ele.getClassName(), L.class.getName())) {
                        startRecord = true;
                    }
                }
                break;
            }
        }
        return sb.toString();
    }
}
