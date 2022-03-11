package com.leetcode.q051;

/**
 * 51. N 皇后<br>
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。<br><br>
 *
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。<br><br>
 *
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。<br>
 *<br><br><br>
 *
 *
 * 示例 1：<br><br><br>
 *
 *
 * 输入：n = 4<br>
 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]<br>
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。<br>
 * 示例 2：<br><br>
 *
 * 输入：n = 1<br>
 * 输出：[["Q"]]<br><br><br>
 *
 *
 * 提示：<br><br>
 *
 * 1 <= n <= 9
 * @author hutianhang
 */
public class Q051 {

    public static void main(String[] args) {
        ISolution solution = new Solution();
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + solution.solveNQueens(i));
        }
    }
}
