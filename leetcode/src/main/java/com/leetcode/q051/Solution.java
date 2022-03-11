package com.leetcode.q051;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hutianhang
 */
public class Solution implements ISolution {

    private static final String EMPTY_FLAG = ".";
    private static final String QUEEN_FLAG = "Q";

    List<List<String>> result = new ArrayList<>();
    List<Integer> cols = new ArrayList<>();
    Set<Integer> pie = new HashSet<>();
    Set<Integer> na = new HashSet<>();

    @Override
    public List<List<String>> solveNQueens(int n) {
        if (n > 0) {
            result.clear();
            dfs(n, 0);
        }
        return result;
    }

    private void dfs(int n, int row) {
        if (row == n) {
            Stream<String> stream = cols.stream().map(col -> EMPTY_FLAG.repeat(col) + QUEEN_FLAG + EMPTY_FLAG.repeat(n - col - 1));
            result.add(stream.collect(Collectors.toList()));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (!cols.contains(col) && !pie.contains(col + row) && !na.contains(col - row)) {
                cols.add(col);
                pie.add(col + row);
                na.add(col - row);
                dfs(n, row + 1);
                cols.remove((Object)col);
                pie.remove(col + row);
                na.remove(col - row);
            }
        }
    }
}
