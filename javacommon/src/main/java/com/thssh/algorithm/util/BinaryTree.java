package com.thssh.algorithm.util;

import java.util.Iterator;

public class BinaryTree<T> implements Iterable<T> {

    static class Entry<T> {
        public Entry(T data) {
            this.data = data;
        }

        public T data;
        public Entry<T> parent;
        public Entry<T> left;
        public Entry<T> right;
    }

    Entry<T> root = null;
    Entry<T> cur = null;
    Queue<Entry<T>> nodes;

    public BinaryTree() {
        nodes = new Queue<>();
    }

    public void add(T data) {
        addNext(new Entry<>(data));
    }

    private void addNext(Entry<T> entry) {
        Entry<T> p = nodes.pick();
        if (p == null) {
            nodes.offer(entry);
            root = cur = entry;
        } else if (p.left == null) {
            // add left
            nodes.offer(entry);
            p.left = entry;
            entry.parent = p;
        } else if (p.right == null) {
            // add right
            nodes.offer(entry);
            p.right = entry;
            entry.parent = p;
        } else {
            nodes.pop();
            addNext(entry);
        }
    }

    class InnerIterator implements Iterator<T> {

        Queue<Entry<T>> helper;

        public InnerIterator() {
            helper = new Queue<>();
            helper.offer(root);
        }

        @Override
        public boolean hasNext() {
            return !helper.isEmpty();
        }

        @Override
        public T next() {
            T data = null;
            Entry<T> it = helper.pop();
            if (it != null) {
                if (it.left != null) helper.offer(it.left);
                if (it.right != null) helper.offer(it.right);
                data = it.data;
            }
            return data;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerIterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tree{ ");
        for (T t : this) sb.append(t).append(" ");
        sb.append("}");
        return sb.toString();
    }
}
