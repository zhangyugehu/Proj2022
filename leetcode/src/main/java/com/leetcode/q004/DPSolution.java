package com.leetcode.q004;

public class DPSolution implements ISolution {

    @Override
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (find(nums1, 0, nums2, 0, left) + find(nums1, 0, nums2, 0, right)) / 2.0;
    }

    /**
     * 12345
     * 06789
     * @param nums1
     * @param p1 nums1的起始位置
     * @param nums2
     * @param p2 nums2的起始位置
     * @param k
     * @return
     */
    public int find(int[] nums1, int p1, int[] nums2, int p2, int k) {
        if (p1 >= nums1.length) {
            return nums2[k + p2];
        }
        if (p2 >= nums2.length) {
            return nums1[k + p1];
        }
        // TODO: 2022/3/7  
        return 0;
    }
}
