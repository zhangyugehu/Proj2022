package com.thssh.sort;

import static com.thssh.sort.Gen.printItems;
import static com.thssh.sort.Gen.printRange;
import static com.thssh.sort.Gen.printRangeAndItems;
import static com.thssh.sort.Gen.swap;

import java.util.Random;

/**
 * @author hutianhang
 */
public class InsertSort {
    public static void main(String[] args) {

        int[] arr = Gen.randomIntegerSeq(new Random().nextInt(8) + 8, 0, 100);

        printRange("S-", arr, -1, -1);
        insertSort(arr);
        printRange("E-", arr, -1, -1);
    }

    private static void insertSort(int[] arr) {
        if (arr.length < 2) {
            System.out.println("already sorted.");
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            printRangeAndItems("insert+", arr, 0, i - 1, i);
            for (int j = 0; j < i; j++) {
                if (arr[i] < arr[j]) {
//                    printRangeAndItems("insert-", arr, 0, i - 1, i, j);
                    swap(arr, i, j);
                }
            }
        }
    }
}
