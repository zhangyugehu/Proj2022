package com.leetcode.q037;

public class Solution implements ISolution {

    int counter;
    @Override
    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0) return;
        counter = 0;
        solve(board);
        System.out.println("try count: " + counter);
    }

    boolean solve(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c;
                            if (solve(board)) {
                                 return true;
                            } else {
                                // 回溯
                                board[i][j] = '.';
                            }
                        }
                    }
                    counter ++;
                    return false;
                }
            }
        }
        counter ++;
        return true;
    }

    boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] != '.' && board[row][i] == c) return false;
            if (board[i][col] != '.' && board[i][col] == c) return false;
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] != '.'
                    && board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false;
        }
        return true;
    }
}
