package com.leetcode.q122;

import java.util.Arrays;

/**
 * 动态规划
 */
public class DPSolution implements ISolution {

    @Override
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        int[][] dp = new int[len][2];

        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        System.out.println(len);
        for (int i = 1; i < len; i++) {
            System.out.println("0: " + dp[i - 1][0] + " <> " + dp[i - 1][1] +  "+" + prices[i] + "=" + (dp[i - 1][1] + prices[i]));
            System.out.println("1: " + dp[i - 1][1] + " <> " + dp[i - 1][0] +  "-" + prices[i] + "=" + (dp[i - 1][0] - prices[i]));
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            System.out.print(i +">>");
            for (int[] ints : dp) {
                System.out.print(Arrays.toString(ints));
            }
            System.out.println();
        }
        return dp[len-1][0];
    }
}