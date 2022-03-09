package com.thssh.algorithm.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LoggedLinkedBlockQueue<T> implements BlockingQueue<T> {
    private static final String TAG = "LoggedLinkedBlockQueue";
    LinkedBlockingQueue<T> delegate;

    public LoggedLinkedBlockQueue(LinkedBlockingQueue<T> delegate) {
        this.delegate = delegate;
    }

    private void log(String msg) {
        System.out.println(TAG + " [" + Thread.currentThread().getName() + "]" + msg);
    }

    @Override
    public boolean add(T t) {
        log("add: " + t);
        return delegate.add(t);
    }

    @Override
    public boolean offer(T t) {
        log("offer: " + t);
        return delegate.offer(t);
    }

    @Override
    public T remove() {
        log("remove: ");
        return delegate.remove();
    }

    @Override
    public T poll() {
        log("poll");
        return delegate.poll();
    }

    @Override
    public T element() {
        log("element");
        return delegate.element();
    }

    @Override
    public T peek() {
        log("peek");
        return delegate.peek();
    }

    @Override
    public void put(T t) throws InterruptedException {
        log("put: " + t);
        delegate.put(t);
    }

    @Override
    public boolean offer(T t, long l, TimeUnit timeUnit) throws InterruptedException {
        log("offer: " + t + ", l: " + l);
        return delegate.offer(t, l, timeUnit);
    }

    @Override
    public T take() throws InterruptedException {
        log("take");
        return delegate.take();
    }

    @Override
    public T poll(long l, TimeUnit timeUnit) throws InterruptedException {
        log("poll time: " + l);
        return delegate.poll(l, timeUnit);
    }

    @Override
    public int remainingCapacity() {
        return delegate.remainingCapacity();
    }

    @Override
    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return delegate.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return delegate.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return delegate.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return delegate.retainAll(collection);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return delegate.toArray(t1s);
    }

    @Override
    public int drainTo(Collection<? super T> collection) {
        return delegate.drainTo(collection);
    }

    @Override
    public int drainTo(Collection<? super T> collection, int i) {
        return delegate.drainTo(collection, i);
    }
}