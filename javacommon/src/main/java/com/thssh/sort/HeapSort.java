package com.thssh.sort;

import static com.thssh.sort.Gen.printItems;
import static com.thssh.sort.Gen.printRange;
import static com.thssh.sort.Gen.swap;

import java.util.Random;

public class HeapSort {
    public static void main(String[] args) {

        int[] arr = Gen.randomIntegerSeq(new Random().nextInt(8) + 2, 0, 100);

        printRange("S-", arr, -1, -1);
        heapSort(arr);
        printRange("E-", arr, -1, -1);
    }

    private static void heapSort(int[] arr) {
        int len = arr.length;
        buildMaxHeap(arr, len);

        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);
            len --;
            heapify(arr, 0, len);
        }
    }

    private static void buildMaxHeap(int[] arr, int len) {
        int floor = (int) Math.floor(len >> 1);
        for (int i = floor; i >= 0; i--) {
            heapify(arr, i, len);
        }
    }

    private static void heapify(int[] arr, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;


        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, len);
        }
    }
}
