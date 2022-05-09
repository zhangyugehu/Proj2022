package com.leetcode.sort;

public class Sorter {

    public static <T extends ISorter> void sort(T sorter, int[] arr) {
        long start = System.currentTimeMillis();
        sorter.sort(arr);
        System.out.println("sorted in " + (System.currentTimeMillis() - start) + "ms");
    }
}
