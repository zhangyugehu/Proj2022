package com.thssh.lock;

import com.thssh.algorithm.util.Timer;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("start");
        Timer.countdown();
        Timer.delay(5000, latch::countDown);
        latch.await();
        System.out.println("end");
    }
}
