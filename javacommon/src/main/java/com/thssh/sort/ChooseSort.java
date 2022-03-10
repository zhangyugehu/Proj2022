package com.thssh.sort;

import static com.thssh.sort.Gen.printItems;
import static com.thssh.sort.Gen.printRange;
import static com.thssh.sort.Gen.printRangeAndItems;
import static com.thssh.sort.Gen.swap;

import java.util.Random;

/**
 * @author hutianhang
 */
public class ChooseSort {
    public static void main(String[] args) {

        int[] arr = Gen.randomIntegerSeq(new Random().nextInt(8) + 8, 0, 100);

        printRange("S-", arr, -1, -1);
        chooseSort(arr);
        printRange("E-", arr, -1, -1);
    }

    private static void chooseSort(int[] arr) {
        if (arr.length < 2) {
            System.out.println("already sorted.");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
//            printRangeAndItems("choose", arr, i, arr.length, i);
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    printRangeAndItems("choose", arr, i, arr.length, j);
                    swap(arr, i, j);
                }
            }
        }
    }
}
