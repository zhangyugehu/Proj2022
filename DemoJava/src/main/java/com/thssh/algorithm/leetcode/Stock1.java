package com.thssh.algorithm.leetcode;

import java.util.Arrays;

public class Stock1 {

    interface Solution {
        int maxProfit(int[] prices);
    }

    public static void main(String[] args) {
        Solution solution = new DPSolution();

        int[] prices = {
                7, 1, 5, 3, 6, 4
        };
        System.out.println("prices: " + Arrays.toString(prices));
        long before = System.currentTimeMillis();
        int maxProfit = solution.maxProfit(prices);
        System.out.println("time: " + (System.currentTimeMillis() - before) + "ms");
        System.out.println("maxProfit: " + maxProfit);
    }

    /**
     * 暴力破解
     */
    static class BoomSolution implements Solution {

        @Override
        public int maxProfit(int[] prices) {
            return 0;
        }
    }

    /**
     * 动态规划
     */
    static class DPSolution implements Solution {
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

            for (int i = 1; i < len; i++) {
                int[] yesterday = dp[i - 1];
                int[] today = dp[i];
                // 今天卖股票的收益
                int todayStock = yesterday[STOCK] + prices[i];
                // 今天买股票
                int todayCash = yesterday[CASH] - prices[i];
                today[CASH] = Math.max(yesterday[CASH], todayStock);
                today[STOCK] = Math.max(yesterday[STOCK], todayCash);
            }
            return dp[len-1][CASH];
        }
    }
}
