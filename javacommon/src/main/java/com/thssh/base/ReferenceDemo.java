package com.thssh.base;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by hutianhang on 2022/2/16
 */
public class ReferenceDemo {

    public static void main(String[] args) {
        ReferenceDemo demo = new ReferenceDemo();
        demo.test2();
    }

    // 创建一个引用队列
    ReferenceQueue<Object> queue = new ReferenceQueue<>();

    private void test() {
        // 创建一个对象
        Object obj = new Object();
        // 创建一个弱引用，并指向这个对象，并且将引用队列传递给弱引用
        WeakReference<Object> reference = new WeakReference<>(obj, queue);
        SoftReference<Object> softRef = new SoftReference<>(obj, queue);
        // 打印出这个弱引用，为了跟gc之后queue里面的对比证明是同一个
        System.out.println("这个弱引用是:" + reference);
        // gc一次看看(毛用都没)
        System.gc();
        // 打印队列(应该是空)
        printlnQueue("before");

        // 先设置obj为null，obj可以被回收了
        obj = null;
        // 再进行gc，此时obj应该被回收了，那么queue里面应该有这个弱引用了
        System.gc();
        // 再打印队列
        printlnQueue("after");
    }

    private void test2() {
        Object obj = new Object();
        WeakReference<Object> reference = new WeakReference<>(obj, queue);
        // gc一次看看(毛用都没)
        System.gc();
        // 打印队列(应该是空)
        printlnQueue("before");

        // 先设置obj为null，obj可以被回收了
        obj = null;
        // 再进行gc，此时obj应该被回收了，那么queue里面应该有这个弱引用了
        System.gc();
        // 再打印队列
        printlnQueue("after");
    }

    private void printlnQueue(String tag) {
        System.out.print(tag);
        Object obj;
        // 循环打印引用队列
        while ((obj = queue.poll()) != null) {
            System.out.println(": " + obj);
        }
        System.out.println();
    }
}
