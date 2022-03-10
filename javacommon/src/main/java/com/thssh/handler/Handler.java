package com.thssh.handler;

import java.util.concurrent.BlockingQueue;

public class Handler {
    private final Looper mLooper;
    private final BlockingQueue<Message> mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
    }

    public void handleMessage(Message msg) {}

    public void sendMessage(Message message) {
        message.target = this;
        mQueue.offer(message);
    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}
