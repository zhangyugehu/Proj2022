package com.leetcode.q022;

import java.util.*;

public class Solution implements ISolution {

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
    @Override
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        _gen(result, 0, 0, n, "");
        return result;
    }

    private void _gen(List<String> result, int l, int r, int n, String once) {
        if (l == n && r == n) {
            result.add(once);
        }
        if (l < n) {
            _gen(result, l + 1, r, n, once + "(");
        }
        if (r < n && r < l) {
            _gen(result, l, r + 1, n, once + ")");
        }
    }
}
