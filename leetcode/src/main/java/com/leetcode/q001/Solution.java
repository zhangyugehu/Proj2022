package com.leetcode.q001;

import java.util.HashMap;
import java.util.Map;

public class Solution implements ISolution {
    @Override
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> pairDic = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            Integer pairIdx = pairDic.get(cur);
            if (pairIdx == null) {
                pairDic.put(target - nums[i], i);
            } else if (nums[pairIdx] + cur == target){
                return new int[] {pairIdx, i};
            }
        }
        return new int[0];
    }
}
