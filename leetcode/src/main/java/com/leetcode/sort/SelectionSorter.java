package com.leetcode.sort;

public class SelectionSorter implements ISorter {
    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            Helper.swap(arr, i, minIndex);
        }
    }
}
