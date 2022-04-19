package com.thssh.fakeleakcanary;

import android.app.Activity;
import android.app.Application;
import android.os.*;
import android.util.Log;

import android.util.Printer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by hutianhang on 2022/2/16
 */
public class App extends Application implements Printer {

    private final Map<String, WatchReference<Activity>> watchDog = new HashMap<>();
    private final ReferenceQueue<Activity> queue = new ReferenceQueue<>();

    private final ActivityLifecycleCallbacks lifecycleCallbacks = new SimpleActivityLifecycleCallbacks() {

        private Handler watcher;

        @Override
        protected void init() {
            HandlerThread ht = new HandlerThread("watchDog");
            ht.start();
            watcher = new Handler(ht.getLooper());
            loopPrintTimer();
        }

        private final long START_TIME = SystemClock.uptimeMillis();
        private void loopPrintTimer() {
            Log.i(TAG, (SystemClock.uptimeMillis() - START_TIME) / 1000 + "s");
            watcher.postDelayed(this::loopPrintTimer, 5000);
        }

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            String key = UUID.randomUUID().toString();
            watchDog.put(key, new WatchReference<>(key, activity, "onActivityCreated", queue));
            Runtime.getRuntime().gc();
            watcher.postDelayed(new Check(queue, watchDog), 3000);
        }
    };

    long start = -1;
    @Override
    public void println(String s) {
        if (s.startsWith(">")) {
            start = SystemClock.uptimeMillis();
        } else if (s.startsWith("<") && start != -1) {
            StringBuilder sb = new StringBuilder("\nStackTrace: \n");
            for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                sb.append(" (").append(element.getFileName()).append(":").append(element.getLineNumber()).append(") ").append("\n");
                sb.append(element.getClassName()).append(".").append(element.getMethodName()).append("\n");
            }
            Log.d(TAG, "delta: " + (SystemClock.uptimeMillis() - start) + sb);
            start = -1;
        }
    }

    static class Check implements Runnable {
        private final ReferenceQueue<Activity> queue;
        private final Map<String, WatchReference<Activity>> watchDog;


        public Check(ReferenceQueue<Activity> queue, Map<String, WatchReference<Activity>> watchDog) {
            this.watchDog = watchDog;
            this.queue = queue;
        }

        @Override
        public void run() {
            Runtime.getRuntime().gc();
            SystemClock.sleep(1000);
            Reference<? extends Activity> next;
            do {
                next = queue.poll();
                if (next instanceof WatchReference) {
                    watchDog.remove(((WatchReference) next).key);
                }
            } while (next != null);
            if (watchDog.isEmpty()) {
                Log.d(TAG, "gc success");
            } else {
                Log.d(TAG, "Leaked!!!");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    watchDog.forEach((k, val)-> Log.i(TAG, k + ": " + val));
                }
            }
        }
    }

    public static final String TAG = "Proj2022";

    @Override
    public void onCreate() {
        super.onCreate();

        getMainLooper().setMessageLogging(this);

        registerActivityLifecycleCallbacks(lifecycleCallbacks);

    }
}
