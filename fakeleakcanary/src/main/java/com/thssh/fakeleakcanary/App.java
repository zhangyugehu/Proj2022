package com.thssh.fakeleakcanary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by hutianhang on 2022/2/16
 */
public class App extends Application {

    private final ReferenceQueue<Object> queue = new ReferenceQueue<>();

    private final ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            WeakReference<Activity> reference = new WeakReference<>(activity, queue);
            activity = null;
            System.gc();
            handler.postDelayed(() -> Log.i(TAG, "onActivityDestroyed: " + reference.get()), 1000);
        }
    };

    public static final String TAG = "Proj2022";
    private void printlnQueue(String tag) {
        StringBuilder sb = new StringBuilder("[QUEUE]");
        sb.append(tag);
        Object obj;
        // 循环打印引用队列
        while ((obj = queue.poll()) != null) {
            sb.append(": ").append(obj);
        }
        Log.i(TAG, sb.toString());
    }

    private Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        handler = new Handler(Looper.getMainLooper());
        registerActivityLifecycleCallbacks(lifecycleCallbacks);

        loopPrintQueue();

    }

    private void loopPrintQueue() {
        printlnQueue(String.valueOf(System.currentTimeMillis()));
        handler.postDelayed(this::loopPrintQueue, 5000);
    }
}
