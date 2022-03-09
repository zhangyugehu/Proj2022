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
}
