package com.leetcode.sort;

public class InsertionSorter implements ISorter{
    @Override
    public void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int next = arr[i];
            int j = i;
            // 13579 2
            // 12357 9
            for (; j > 0 && next < arr[j - 1]; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = next;
        }
    }
}
