package com.thssh.test;

import java.util.concurrent.Executors;

public class ResetListener {
    public static void main(String[] args) {
        ResetListener resetListener = new ResetListener();
        Executors.newSingleThreadExecutor().execute(() -> {
            for (int i = 0; i < 100; i++) {
                resetListener.reset();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    Runnable listener;
    public void setListener(Runnable listener) {
        this.listener = listener;
        System.out.println(listener.hashCode());
    }

    public void reset() {
        setListener(() -> System.out.println("do one thing listener " + System.currentTimeMillis()));
    }
}
