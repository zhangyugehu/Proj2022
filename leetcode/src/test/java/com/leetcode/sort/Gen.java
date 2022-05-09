package com.leetcode.sort;

import java.util.Random;

public class Gen {
    public static int[] genArray(int N) {
        Random random = new Random();
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            result[i] = random.nextInt();
        }
        return result;
    }
}
