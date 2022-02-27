package com.leetcode.q001;

import java.util.Arrays;

/**
 * 1. 两数之和<br>
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。<br><br>
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。<br><br>
 *
 * 你可以按任意顺序返回答案。<br>
 *<br><br><br>
 *
 *
 * 示例 1：<br><br>
 *
 * 输入：nums = [2,7,11,15], target = 9<br>
 * 输出：[0,1]<br>
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。<br>
 * 示例 2：<br>
 *<br>
 * 输入：nums = [3,2,4], target = 6<br>
 * 输出：[1,2]<br>
 * 示例 3：<br>
 *<br>
 * 输入：nums = [3,3], target = 6<br>
 * 输出：[0,1]<br>
 *<br><br>
 *
 * 提示：<br><br>
 *
 * 2 <= nums.length <= 104<br>
 * -109 <= nums[i] <= 109<br>
 * -109 <= target <= 109<br>
 * 只会存在一个有效答案<br>
 * 进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？<br>
 */
public class Q001 {
    public static void main(String[] args) {
        ISolution solution = new Solution();
        printResult(solution.twoSum(new int[] {
                2,7,11,15
        }, 9));
    }

    private static void printResult(int[] twoSum) {
        System.out.println(Arrays.toString(twoSum));
    }
}
