package com.leetcode.free.merge;

import java.util.Arrays;

/**
 * @author hutianhang
 */
public class MergeSort {
    public static void main(String[] args) {

//        int[] arr1 = Gen
        int[] arr1 = new int[] {8, 7, 6, 5, 1, 2, 3, 4};
        int[] result = new int[arr1.length];

        sort(arr1, 0, arr1.length - 1, result);

        System.out.println(Arrays.toString(arr1));
    }

    private static void sort(int[] arr, int left, int right, int[] result) {
        if (left < right) {
            System.out.println(left + "~" + right + " start");
            int mid = (left + right) / 2;
            sort(arr, left, mid, result);
            sort(arr, mid + 1, right, result);
            merge(arr, left, mid, right, result);
        } else {
            System.out.println(left + "~" + right + " end");
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] result) {
        System.out.println("merge " + left + " " + mid + " " + right);
        int p1 = left;
        int p2 = mid + 1;
        int pr = 0;
        while (p1 <= mid && p2 <= right) {
            if (arr[p1] <= arr[p2]) {
                result[pr++] = arr[p1++];
            } else {
                result[pr++] = arr[p2++];
            }
        }

        while (p1 <= mid) {
            result[pr++] = arr[p1++];
        }
        while (p2 <= right) {
            result[pr++] = arr[p2++];
        }

        System.out.println("sort " + left + "~" + right + " " + Arrays.toString(result));

        pr = 0;
        for (int i = left; i <= right; i++) {
            arr[i] = result[pr++];
        }
    }

}
