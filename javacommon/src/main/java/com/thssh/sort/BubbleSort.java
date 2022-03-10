package com.thssh.sort;

import static com.thssh.sort.Gen.printItems;
import static com.thssh.sort.Gen.printRange;
import static com.thssh.sort.Gen.printRangeAndItems;
import static com.thssh.sort.Gen.swap;

import java.util.Random;

/**
 * @author hutianhang
 */
public class BubbleSort {
    public static void main(String[] args) {

        int[] arr = Gen.randomIntegerSeq(new Random().nextInt(8) + 2, 0, 100);

        printRange("S-", arr, -1, -1);
        bubbleSort(arr);
        printRange("E-", arr, -1, -1);
    }

    private static void bubbleSort(int[] arr) {
        if (arr.length < 2) {
            System.out.println("already sorted.");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
//            printRange("bubble", arr, i, arr.length);
            for (int j = arr.length - 1; j > i; j--) {
                printItems("bubble", arr, j - 1, j);
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }
}
