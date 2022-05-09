package com.leetcode.sort;

public class Helper {
    public static void swap(int[] arr, int a, int b) {
        if (a != b) {
            int tmp = arr[a];
            arr[a] = arr[b];
            arr[b] = tmp;
        }
    }
}
