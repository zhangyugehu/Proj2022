package com.thssh.lock;

import com.thssh.algorithm.util.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Timer.countdown();
        Timer.delay(5000, () -> {
            condition.signalAll();
            lock.unlock();
        }, lock::lock);

        try {
//            boolean b = lock.tryLock(5, TimeUnit.SECONDS);
//            System.out.println("tryLock " + b);
            condition.await();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
