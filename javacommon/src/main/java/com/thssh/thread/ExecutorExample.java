package com.thssh.thread;

import com.thssh.algorithm.util.LoggedLinkedBlockQueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorExample {
    private static Executor executor;

    private static Executor getExecutor() {
        if (null == executor) {
            synchronized (ExecutorExample.class) {
                if (null == executor) {
                    executor = createExecutor();
                }
            }
        }
        return executor;
    }

    static AtomicInteger tIndex = new AtomicInteger(0);
    private static Executor createExecutor() {
        // < core
        return new ThreadPoolExecutor(
                1,
                3,
                1,
                TimeUnit.NANOSECONDS,
                new LoggedLinkedBlockQueue<>(new LinkedBlockingQueue<>(5)),
                runnable -> new Thread(runnable, "t-" + tIndex.getAndIncrement()),
                (runnable, threadPoolExecutor) -> log("runnable: " + runnable + " rejected")
        );
    }

    static abstract class IndexedRunnable implements Runnable {

        int index;

        public IndexedRunnable(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return "IndexedRunnable{" +
                    "index=" + index +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            log("============start: " + finalI + "============");
            getExecutor().execute(new IndexedRunnable(finalI) {
                @Override
                public void run() {
                    int rInt = new Random().nextInt(10) + 1;
                    log("index: " + index + ", r: " + rInt);
                    try {
                        Thread.sleep(rInt * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log("============end: " + finalI + "============");
                }
            });
            Thread.sleep((new Random().nextInt(1) + 1) * 800);
        }
    }

    private static void log(String msg) {
        System.out.println(Thread.currentThread().getName() + "]" + msg);
    }
}
