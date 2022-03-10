package com.thssh.sort;

import static com.thssh.sort.Gen.printRange;

import java.util.Random;

/**
 * @author hutianhang
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = Gen.randomIntegerSeq(new Random().nextInt(8) + 2, 0, 100);

        printRange("S-", arr, -1, -1);

        sort(arr, 0, arr.length - 1);

        printRange("E-", arr, -1, -1);
    }

    private static void sort(int[] arr, int l, int r) {
        if (arr.length < 2) {
            System.out.println("already sorted.");
            return;
        }
        if (l < r) {
            int mid = (l + r) >> 1;
            sort(arr, l, mid);
            sort(arr, mid + 1, r);
            merge(arr, l, mid, r);
        }
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        printRange("merge", arr, l, r);
        int[] help = new int[r - l + 1];
        int p = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r) {
            if (arr[p1] <= arr[p2]) {
                help[p++] = arr[p1++];
            } else {
                help[p++] = arr[p2 ++];
            }
        }

        while (p1 <= mid) {
            help[p++] = arr[p1++];
        }

        while (p2 <= r) {
            help[p++] = arr[p2++];
        }

        p1 = l;
        for (int cur : help) {
            arr[p1++] = cur;
        }
    }
}
