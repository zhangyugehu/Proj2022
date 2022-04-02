package com.thssh.commonlib.timer;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @author hutianhang
 */
public class IntervalTimer implements LifecycleListener, Runnable {

    public static IntervalTimer with(FragmentActivity activity, long timeout, Runnable callback) {
        IntervalTimer timer = new IntervalTimer(timeout, callback);
        IntervalRetriever retriever = IntervalRetriever.get();
        retriever.get(activity).addListener(timer);
        return timer;
    }

    public static IntervalTimer with(Fragment fragment, long timeout, Runnable callback) {
        IntervalTimer timer = new IntervalTimer(timeout, callback);
        IntervalRetriever retriever = IntervalRetriever.get();
        retriever.get(fragment).addListener(timer);
        return timer;
    }

    public static void pause(IntervalTimer... timers) {
        for (IntervalTimer timer : timers) {
            if (timer != null) {
                timer.stop();
            }
        }
    }

    public static void resume(IntervalTimer... timers) {
        for (IntervalTimer timer : timers) {
            if (timer != null) {
                timer.start();
            }
        }
    }

    public static void stop(IntervalTimer... timers) {
        for (IntervalTimer timer : timers) {
            if (timer != null) {
                timer.stop();
            }
        }
    }

    Handler worker;
    Runnable callback;
    long timeout;

    public IntervalTimer() {
        HandlerThread workerThread = new HandlerThread("interval_thread@" + hashCode());
        workerThread.start();
        worker = new Handler(Looper.myLooper());
    }

    public IntervalTimer(long timeout, Runnable callback) {
        this();
        this.callback = callback;
        this.timeout = timeout;
    }

    public IntervalTimer start() {
        startReal();
        return this;
    }

    public void resetTimeout(long timeout) {
        this.timeout = timeout;
        startReal();
    }

    public void stop() {
        worker.removeCallbacks(this);
    }

    @Override
    public void run() {
        if (callback != null) {
            callback.run();
        }
        startReal();
    }

    private void startReal() {
        worker.removeCallbacks(this);
        worker.postDelayed(this, timeout);
    }

    @Override
    public void onStart() {
        start();
    }

    @Override
    public void onPause() {
        pause(this);
    }

    @Override
    public void onStop() {
        stop(this);
    }
}
