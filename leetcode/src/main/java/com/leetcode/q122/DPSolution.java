package com.leetcode.q122;

import java.util.Arrays;

/**
 * 动态规划
 */
public class DPSolution implements ISolution {
    // 记录改日持有现金最高收益
    private static final int CASH = 0;
    // 记录改日持有股票最高收益
    private static final int STOCK = 1;

    @Override
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        int[][] dp = new int[len][2];

        dp[0][CASH] = 0;
        dp[0][STOCK] = -prices[0];

        System.out.println(len);
        for (int i = 1; i < len; i++) {
            dp[i][CASH] = Math.max(dp[i - 1][CASH], dp[i - 1][STOCK] + prices[i]);
            dp[i][STOCK] = Math.max(dp[i - 1][STOCK], dp[i - 1][CASH] - prices[i]);
            System.out.print(i +">>");
            for (int j = 0; j < dp.length; j++) {
                System.out.print(Arrays.toString(dp[j]));
            }
            System.out.println();
        }
        return dp[len-1][CASH];
    }
}