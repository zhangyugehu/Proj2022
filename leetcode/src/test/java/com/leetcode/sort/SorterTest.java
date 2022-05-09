package com.leetcode.sort;

import org.junit.jupiter.api.Test;

public class SorterTest {

    private void assertSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                throw new IllegalStateException("index for " +
                        i + ": " + arr[i] + " vs " +
                        (i - 1) + ": " + arr[i - 1] +
                        " is not sorted"
                );
            }
        }
    }

    @Test
    void assertSortedTest() {
        assertSorted(new int[] {1, 3, 5, 7, 9});
    }

    @Test
    void InsertionSorterTest() {
        int[] arr = Gen.genArray(100000);
        Sorter.sort(new InsertionSorter(), arr);
        assertSorted(arr);
    }

    @Test
    void SelectionSorterTest() {
        int[] arr = Gen.genArray(100000);
        Sorter.sort(new SelectionSorter(), arr);
        assertSorted(arr);
    }

    @Test
    void BubbleSorterTest() {
        int[] arr = Gen.genArray(100000);
        Sorter.sort(new BubbleSorter(), arr);
        assertSorted(arr);
    }
}
