package com.leetcode.q022;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 22. 括号生成<br>
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。<br>
 *<br><br><br>
 *
 *
 * 示例 1：<br><br>
 *
 * 输入：n = 3<br>
 * 输出：["((()))","(()())","(())()","()(())","()()()"]<br>
 * 示例 2：<br>
 *<br>
 * 输入：n = 1<br>
 * 输出：["()"]<br>
 *<br><br>
 *
 * 提示：<br>
 *<br>
 * 1 <= n <= 8
 */
public class Q022 {
    public static void main(String[] args) {
        ISolution solution = new Solution();
        printResult(solution.generateParenthesis(3));
    }

    private static void printResult(List<String> parenthesis) {
        System.out.println(Arrays.toString(parenthesis.toArray()));
    }
}
