package com.thssh.algorithm.util;

public class Queue<T> {

    static class Node<T> {
        public Node(T data) {
            this.data = data;
        }

        T data;
        Node<T> next;
    }

    Node<T> head = null, tail = null;

    public void offer(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    public T pop() {
        T data = null;
        if (head != null) {
            data = head.data;
            head = head.next;
            if (head == null) tail = null;
        }
        return data;
    }

    public T pick() {
        if (head == null) return null;
        return head.data;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Queue{");
        Node<T> it = head;
        String split = ", ";
        boolean fix = false;
        while (it != null) {
            sb.append(it.data).append(split);
            it = it.next;
            fix = true;
        }
        if (fix) sb.delete(sb.length() - split.length(), sb.length());
        sb.append("}");
        return sb.toString();
    }
}
