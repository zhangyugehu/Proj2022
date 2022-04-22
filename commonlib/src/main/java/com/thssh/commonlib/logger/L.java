package com.thssh.commonlib.logger;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class L {

    public interface Logger {
        void d(String tag, String msg);
    }

    static class DefaultLogger implements Logger {

        @Override
        public void d(String tag, String msg) {
            Log.d(tag, msg);
        }
    }

    private static final Logger DEFAULT_LOGGER = new DefaultLogger();
    private static Logger sLogger = DEFAULT_LOGGER;


    public static void setLogger(Logger logger) {
        sLogger = logger;
    }

    public static void resetLogger() {
        sLogger = DEFAULT_LOGGER;
    }

    private static final String TAG = "Proj2022";

    static Handler h = new Handler(Looper.getMainLooper());
    static AtomicBoolean flag = new AtomicBoolean(true);

    static Map<String, Object[]> dDiffLast = new HashMap<>();

    public static void dDiff(String tag, Object... msgQueue) {
        if (!Arrays.equals(dDiffLast.get(tag), msgQueue)) {
            d(msgQueue);
            dDiffLast.put(tag, msgQueue);
        }
    }

    public static void dLatest(Object... msgQueue) {
        dLatest(1000, msgQueue);
    }

    public static void dLatest(int delay, Object... msgQueue) {
        if (flag.getAndSet(false)) {
            d(msgQueue);
        }
        h.postDelayed(() -> {
            h.removeCallbacksAndMessages(null);
            flag.set(true);
        }, delay);
    }

    public static void d(Object... msgQueue) {
        StringBuilder sb = new StringBuilder();
        for (Object msg : msgQueue) {
            if (msg != null) {
                sb.append(msg).append(" ");
            }
        }
        sLogger.d(TAG, sb.toString());
    }

    public static void td(Object... msgQueue) {
        StringBuilder sb = new StringBuilder();
        for (Object msg : msgQueue) {
            if (msg != null) {
                sb.append(msg).append(" ");
            }
        }
        sLogger.d(TAG, tNamePrefix() + sb);
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
                        if (++depthIdx > depth) {
                            break;
                        }
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
