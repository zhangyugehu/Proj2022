package com.leetcode.q004;

public class Solution implements ISolution {

    @Override
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int fullLength = nums1.length + nums2.length;
        int mid;
        boolean oneMid = false;
        if (fullLength % 2 == 0) {
            mid = fullLength / 2;
        } else {
            mid = fullLength / 2;
            oneMid = true;
        }
        int prev = 0;
        int last = 0;
        for (int ptr1 = 0, ptr2 = 0; ptr1 + ptr2 < mid + 1;) {
            prev = last;
            if (ptr1 < nums1.length && (ptr2 >= nums2.length || nums1[ptr1] < nums2[ptr2])) {
                last = nums1[ptr1++];
            } else if (ptr2 < nums2.length){
                last = nums2[ptr2 ++];
            }
        }
        if (oneMid) {
            return last;
        } else {
            return (last + prev) / 2.0;
        }
    }
}
