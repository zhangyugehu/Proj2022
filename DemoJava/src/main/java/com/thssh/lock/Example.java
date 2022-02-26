package com.thssh.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Example {
    static int count = 0;
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                System.out.println("+: " + (++count));

                lock.unlock();
            }
        }, "t-1").start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                System.out.println("-: " + (--count));
                lock.unlock();
            }
        }, "t-2").start();
    }
}
