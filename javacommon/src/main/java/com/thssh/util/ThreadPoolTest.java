package com.thssh.util;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试ThreadPool 核心线程数、最大线程数、队列大小配合关系
 *
 * 1. 无限队列: 始终执行核心线程数个线程
 * 2. 队列有限: 先执行 核心线程 个线程，小于 队列大小的任务加入队列.
 * 大于队列大小后 开启非核心线程执行后续任务
 * 大于最大线程数 后 执行拒绝策略
 * 3. 基于2, 任务执行完核心和非核心线程同时执行队列中的任务
 * Created by hutianhang on 2022/5/3
 */
public class ThreadPoolTest {
    private static LinkedBlockingQueue queue;

    static class InnerQueue extends LinkedBlockingQueue<InnerRunnable> {
        public InnerQueue(int capacity) {
            super(capacity);
        }

        @Override
        public boolean offer(InnerRunnable innerRunnable) {
            System.out.println("offer " + innerRunnable.index);
            return super.offer(innerRunnable);
        }

        @Override
        public InnerRunnable take() throws InterruptedException {
            System.out.println("take");
            return super.take();
        }
    }

    public static void main(String[] args) {
        timerPrinter();

        loopAddRunnable();

        for (int i = 0; i < size; i++) {
            executor.execute(new InnerRunnable(i));
        }

    }

    static int size = 6;

    static ExecutorService executor = new ThreadPoolExecutor(
            1,
            10,
            1, TimeUnit.SECONDS,
            (queue = new InnerQueue(3)), new ThreadFactory() {


                final AtomicInteger counter = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    thread.setName("Thread-" + counter.getAndIncrement());
                    return thread;
                }
            }, (runnable, threadPoolExecutor) -> {
                int tag = -1;
                if (runnable instanceof InnerRunnable) {
                    tag = ((InnerRunnable) runnable).index;
                }
                System.out.println("reject runnable-" + tag + "-" + runnable);
            });

    static int sec = 0;
    private static void timerPrinter() {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("time: " + (sec ++));
                    Thread.sleep(999);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void loopAddRunnable() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(15_000);
                    System.out.println("offer a new Runnable.");
                    executor.execute(new InnerRunnable(size++));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class InnerRunnable implements Runnable {

        int index;

        Random r = new Random();

        public InnerRunnable(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "]" + index + " in executor execute. START");
            try {
//                Thread.sleep((1 + r.nextInt(3)) * 1000);
                Thread.sleep(5_000);
                System.out.println(Thread.currentThread().getName() + "]" + index + " in executor execute. END");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
