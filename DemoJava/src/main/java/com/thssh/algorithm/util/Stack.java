package com.thssh.algorithm.util;

public class Stack<T> {

    static class Node<T> {
        public Node(T data) {
            this.data = data;
        }

        T data;
        Node<T> next;
    }

    private Node<T> first = null;

    public void offer(T data) {
        Node<T> node = new Node<>(data);
        if (first == null) {
            first = node;
        } else {
            Node<T> tmp = first;
            first = node;
            first.next = tmp;
        }
    }

    public T pop() {
        T result = null;
        if (first != null ) {
            result = first.data;
            first = first.next;
        }
        return result;
    }

    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack{");
        Node<T> it = first;
        boolean fix = false;
        while (it != null) {
            sb.append(it.data).append(", ");
            it = it.next;
            fix = true;
        }
        if (fix) sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }
}
