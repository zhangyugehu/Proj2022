package com.leetcode.q004;

public interface ISolution {
    /**
     * 4. 寻找两个正序数组的中位数 <br>
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。<br>
     *<br>
     * 算法的时间复杂度应该为 O(log (m+n)) 。<br>
     *<br><br><br>
     *
     *
     * 示例 1：<br><br>
     *
     * 输入：nums1 = [1,3], nums2 = [2]<br>
     * 输出：2.00000<br>
     * 解释：合并数组 = [1,2,3] ，中位数 2<br>
     * 示例 2：<br><br>
     *
     * 输入：nums1 = [1,2], nums2 = [3,4]<br>
     * 输出：2.50000<br>
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5<br><br><br><br>
     *
     *
     *
     *
     * 提示：<br><br>
     *
     * nums1.length == m<br>
     * nums2.length == n<br>
     * 0 <= m <= 1000<br>
     * 0 <= n <= 1000<br>
     * 1 <= m + n <= 2000<br>
     * -106 <= nums1[i], nums2[i] <= 106<br>
     */
    double findMedianSortedArrays(int[] nums1, int[] nums2);
}
