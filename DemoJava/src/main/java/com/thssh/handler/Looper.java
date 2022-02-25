package com.thssh.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Looper {
    private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    private static Looper mMainLooper;

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    public static void prepareMainLooper() {
        prepare();
        if (mMainLooper != null) {
            throw new IllegalStateException("The main Looper has already been prepared.");
        }
        mMainLooper = myLooper();
    }

    BlockingQueue<Message> mQueue;

    public Looper() {
        mQueue = new LinkedBlockingQueue<>();
    }

    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        System.out.println("Looper START!!!");
        for (;;) {
            if (me.mQueue == null) break;
            Message msg = me.mQueue.poll();
            if (msg != null) {
                msg.target.dispatchMessage(msg);
            }
        }
        System.out.println("Looper END!!!");
    }
}
