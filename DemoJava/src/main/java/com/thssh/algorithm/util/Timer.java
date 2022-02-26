package com.thssh.algorithm.util;

public class Timer {

    public static void delay(long time, Runnable timeout) {
        delay(time, timeout, null);
    }

    public static void delay(long time, Runnable timeout, Runnable before) {
        new Thread(() -> {
            try {
                if (before != null) before.run();
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                timeout.run();
            }
        }).start();
    }

    public static void countdown() {
        new Thread(() -> {
            int counter = 0;
            while (true) {
                try {
                    System.out.println((counter++) + "s");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
    }
}
