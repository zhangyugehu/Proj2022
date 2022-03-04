package com.leetcode.q122;

import java.util.Arrays;

public class BoomSolution implements ISolution {

    int maxProfit = 0;

    @Override
    public int maxProfit(int[] prices) {
        dayProfit(prices, 0, new int[prices.length], 0);
        return maxProfit;
    }

    int count = 0;
    private void dayProfit(int[] prices, int day, int[] status, int profit) {
        int days = prices.length;
        if (day == days) {
            maxProfit = Math.max(maxProfit, profit);
//            System.out.println(count++ + ": " + Arrays.toString(status) + " = " + profit);
        }

        // 不交易
        if (day < days) {
            if (day > 0) {
                status[day] = status[day - 1];
            }
            dayProfit(prices, day + 1, status, profit);
        }

        // 交易
        if (day < days) {
            int price = 0;
            if (day == 0 || status[day - 1] != -1) {
                // 买
                price = -prices[day];
                status[day] = -1;
            } else if (status[day - 1] == -1){
                // 卖
                // 可以再买
                status[day] = 1;
                price = prices[day];
            }
            dayProfit(prices, day + 1, status, profit + price);
        }
    }
}