package com.thssh.commonlib.utils;

import android.text.TextUtils;

import java.util.Map;

public class RuntimeUtil {
    public static String getCallStack(int depth) {
        Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
        StringBuilder sb = new StringBuilder();
        boolean startRecord = false;
        int depthIdx = 0;
        for (Map.Entry<Thread, StackTraceElement[]> entry: traces.entrySet()){
            if (Thread.currentThread() == entry.getKey()) {
                for (StackTraceElement ele : entry.getValue()) {
                    if (startRecord) {
                        if (++depthIdx > depth) {
                            break;
                        }
                        sb.append("\n👉")
                                .append(ele.getClassName()).append(".").append(ele.getMethodName())
                                .append("@Line: ").append(ele.getLineNumber())
                                .append("👈");
                    }
                    if (TextUtils.equals(ele.getClassName(), RuntimeUtil.class.getName())) {
                        startRecord = true;
                    }
                }
                break;
            }
        }
        return sb.toString();
    }
}
