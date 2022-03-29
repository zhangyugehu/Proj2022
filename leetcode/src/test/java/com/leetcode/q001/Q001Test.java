package com.leetcode.q001;

import com.leetcode.q001.ISolution;
import com.leetcode.q001.Solution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Q001Test {

    ISolution solution = new Solution();

    @Test
    void test01() {
        Assertions.assertArrayEquals(
                new int[] {0, 1},
                solution.twoSum(new int[]{2,7,11,15}, 9)
        );
    }
    @Test
    void test02() {
        Assertions.assertArrayEquals(
                new int[] {1, 2},
                solution.twoSum(new int[]{3,2,4}, 6)
        );
    }
    @Test
    void test03() {
        Assertions.assertArrayEquals(
                new int[] {0, 1},
                solution.twoSum(new int[]{3, 3}, 6)
        );
    }
}
