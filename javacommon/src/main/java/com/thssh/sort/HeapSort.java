package com.thssh.sort;

import static com.thssh.sort.Gen.printRange;
import static com.thssh.sort.Gen.printRangeAndItems;
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
        // TODO: 2022/3/10
    }
}
