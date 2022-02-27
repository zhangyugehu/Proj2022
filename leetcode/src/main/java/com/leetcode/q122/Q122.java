package com.leetcode.q122;

import java.util.Arrays;

/**
 * 122. 买卖股票的最佳时机 II <br>
 * 给定一个数组 prices ，其中 prices[i] 表示股票第 i 天的价格。<br>
 *<br>
 * 在每一天，你可能会决定购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以购买它，然后在 同一天 出售。<br>
 * 返回 你能获得的 最大 利润 。<br>
 *<br>
 *
 *<br>
 * 示例 1:<br>
 *
 * 输入: prices = [7,1,5,3,6,4]<br>
 * 输出: 7<br>
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。<br>
 *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。<br>
 * 示例 2:<br>
 *<br><br>
 * 输入: prices = [1,2,3,4,5]<br>
 * 输出: 4<br>
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。<br>
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。<br>
 * 示例 3:<br><br>
 *<br>
 * 输入: prices = [7,6,4,3,1]<br>
 * 输出: 0<br>
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。<br>
 *<br><br><br>
 *
 * 提示：<br><br>
 *
 * 1 <= prices.length <= 3 * 104<br>
 * 0 <= prices[i] <= 104<br>
 */
public class Q122 {

    public static void main(String[] args) {
        ISolution solution = new DPSolution();

        int[] prices1 = {
                7, 1, 5, 3, 6, 4
        };

        System.out.println("prices1: " + Arrays.toString(prices1));
        long before = System.currentTimeMillis();
        int maxProfit = solution.maxProfit(prices1);
        System.out.println("time: " + (System.currentTimeMillis() - before) + "ms");
        System.out.println("maxProfit: " + maxProfit);
    }
}
