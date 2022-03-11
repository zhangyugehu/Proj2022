package com.leetcode.q052;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hutianhang
 */
public class Solution implements ISolution {

    int total = 0;
    Set<Integer> cols = new HashSet<>();
    Set<Integer> pie = new HashSet<>();
    Set<Integer> na = new HashSet<>();

    @Override
    public int totalNQueens(int n) {
        if (n > 0) {
            total = 0;
            dfs(n, 0);
        }
        return total;
    }

    private void dfs(int n, int row) {
        if (row == n) {
            total++;
            return;
        }
        for (int col = 0; col < n; col++) {
            if (!cols.contains(col) && !pie.contains(col + row) && !na.contains(col - row)) {
                cols.add(col);
                pie.add(col + row);
                na.add(col - row);
                dfs(n, row + 1);
                cols.remove((Object) col);
                pie.remove(col + row);
                na.remove((col - row));
            }
        }
    }
}
