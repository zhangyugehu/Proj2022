package com.thssh.algorithm;

import com.thssh.algorithm.util.BinaryTree;
import com.thssh.algorithm.util.Queue;
import com.thssh.algorithm.util.Stack;

public class Client {
    public static void main(String[] args) {
//        queue();
//        stack();
        tree();
    }

    private static void queue() {
        Queue<String> queue = new Queue<>();
        int index = 0;
        for (int i = 0; i < 10; i++) {
            queue.offer(String.valueOf(index++));
        }
        System.out.println(queue);
        for (int i = 0; i < 5; i++) {
            queue.pop();
        }
        System.out.println(queue);
        System.out.println("begin clear...");
        while (!queue.isEmpty()) {
            System.out.println("pop: " + queue.pop());
        }
        System.out.println("clear success. " + queue);
    }

    private static void stack() {
        Stack<String> stack = new Stack<>();
        int index = 0;
        for (int i = 0; i < 10; i++) {
            stack.offer(String.valueOf(index++));
        }
        System.out.println(stack);
        for (int i = 0; i < 5; i++) {
            stack.pop();
        }
        System.out.println(stack);
        System.out.println("begin clear...");
        while (!stack.isEmpty()) {
            System.out.println("pop: " + stack.pop());
        }
        System.out.println("clear success. " + stack);
    }

    private static void tree() {

        BinaryTree<String> binaryTree = new BinaryTree<>();
        for (int i = 0; i < 10; i++) {
            binaryTree.add(String.valueOf(i));
        }

        System.out.println(binaryTree);
    }
}