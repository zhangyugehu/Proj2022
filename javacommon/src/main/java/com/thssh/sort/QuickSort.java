package com.thssh.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * @author hutianhang
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = Gen.randomIntegerSeq(new Random().nextInt(10) + 10, 0, 100);

        printRange("origin", arr, -1, -1);
        quickSort(arr, 0, arr.length - 1);

        printRange("pass", arr, -1, -1);
        System.out.println("step: " + step);
    }

    private static void quickSort(int[] arr, int l, int r) {
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

    static int step = 0;
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

    private static void swap(int[] arr, int i, int j) {
        step ++;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void printRange(String tag, int[] arr, int l, int r) {
        System.out.print(tag);
        System.out.print("[ ");
        for (int i = 0; i < arr.length; i++) {
            if (i >= l && i <= r) {
                System.out.print("\033[0;36;1m" + arr[i] + "\033[0m");
            } else {
                System.out.print(arr[i]);
            }
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();
    }
}
