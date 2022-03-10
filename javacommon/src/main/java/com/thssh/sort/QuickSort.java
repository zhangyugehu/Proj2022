package com.thssh.sort;

import static com.thssh.sort.Gen.printRange;
import static com.thssh.sort.Gen.swap;

import java.util.Random;

/**
 * @author hutianhang
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = Gen.randomIntegerSeq(new Random().nextInt(8) + 2, 0, 100);

        printRange("origin", arr, -1, -1);

        quickSort(arr, 0, arr.length - 1);

        printRange("pass", arr, -1, -1);
    }

    private static void quickSort(int[] arr, int l, int r) {
        if (arr.length < 2) {
            System.out.println("already sorted.");
            return;
        }
        if (l < r) {
            printRange("b", arr, l, r);
            int[] pivot = partition(arr, l, r);
            printRange("a", arr, l, r);
            quickSort(arr, l, pivot[0]);
            quickSort(arr, pivot[1], r);
        } else {
            printRange("n", arr, l, r);
        }
    }

    private static int[] partition(int[] arr, int l, int r) {
        int pivot = arr[r];
        int index = l;
        while (index <= r) {
            if (arr[index] < pivot) {
                swap(arr, index++, l++);
            } else if (arr[index] > pivot) {
                swap(arr, index, r--);
            } else {
                index++;
            }
        }
        return new int[] { l - 1, r };
    }
}
