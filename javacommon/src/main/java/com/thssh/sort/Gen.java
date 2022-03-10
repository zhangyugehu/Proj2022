package com.thssh.sort;

import java.util.Random;

public class Gen {

    public static int[] randomIntegerSeq(int size, int start, int end) {
        Random random = new Random();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = random.nextInt(end - start) + start;
        }
        return result;
    }

    public static void printRange(String tag, int[] arr, int l, int r) {
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

    public static void printItems(String tag, int[] arr, int... itemsIdx) {
        System.out.print(tag);
        System.out.print("[ ");
        for (int i = 0; i < arr.length; i++) {
            int idx = 0;
            boolean colorful = false;
            while (idx < itemsIdx.length) {
                if (itemsIdx[idx++] == i) {
                    colorful = true;
                    break;
                }
            }
            if (colorful) {
                System.out.print("\033[0;36;1m" + arr[i] + "\033[0m");
            } else {
                System.out.print(arr[i]);
            }
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void printRangeAndItems(String tag, int[] arr, int l, int r, int... itemsIdx) {
        System.out.print(tag);
        System.out.print("[ ");
        for (int i = 0; i < arr.length; i++) {
            int idx = 0;
            boolean colorful = false;
            while (idx < itemsIdx.length) {
                if (itemsIdx[idx++] == i) {
                    colorful = true;
                    break;
                }
            }
            if (colorful) {
                System.out.print("\033[0;33;1m" + arr[i] + "\033[0m");
            } else if (i >= l && i <= r) {
                System.out.print("\033[0;36;1m" + arr[i] + "\033[0m");
            } else {
                System.out.print(arr[i]);
            }
            System.out.print(" ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
