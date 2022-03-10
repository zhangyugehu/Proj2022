package com.leetcode.q051;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFSSolution implements ISolution {
    List<List<String>> result = new ArrayList<>();
    List<String> cols = new ArrayList<>();
    List<String> pie = new ArrayList<>();
    List<String> na = new ArrayList<>();

    @Override
    public List<List<String>> solveNQueens(int n) {
        dfs(n, 0, new ArrayList<>());
        return genResult(n);
    }

    private void dfs(int n, int row, List<String> cur) {
        if (row >= n) {
            result.add(cur);
        }
        for (int col = 0; col < n; col++) {
            String stringifyCol = String.valueOf(col);
            String stringifyPie = String.valueOf(row + col);
            String stringifyNa = String.valueOf(row - col);
            if (!cols.contains(stringifyCol) && !pie.contains(stringifyCol) && !na.contains(stringifyCol)) {
                cols.add(stringifyCol);
                pie.add(stringifyPie);
                na.add(stringifyNa);
                cur.add(stringifyCol);
                dfs(n, row + 1, cur);
                cols.remove(stringifyCol);
                pie.remove(stringifyPie);
                na.remove(stringifyNa);
            }
        }
    }


    private List<List<String>> genResult(int n) {
        for (List<String> one: result){
            for (int i = 0; i < one.size(); i++) {
                String stringify = one.get(i);
                int count = Integer.parseInt(stringify);
                String sb = ".".repeat(Math.max(0, count)) +
                        "Q" +
                        ".".repeat(Math.max(0, n - count - 1));
                one.set(i, sb);
            }
        }
        return null;
    }

}
