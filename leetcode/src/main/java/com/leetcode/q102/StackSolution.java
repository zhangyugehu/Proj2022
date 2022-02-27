package com.leetcode.q102;

import java.util.ArrayList;
import java.util.List;

public class StackSolution implements Solution {
    @Override
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        addNode(root, result, 0);
        return result;
    }

    private void addNode(TreeNode node, List<List<Integer>> result, int level) {
        if (node != null) {
            if (result.size() <= level) {
                result.add(level, new ArrayList<>());
            }
            List<Integer> list = result.get(level);
            list.add(node.val);
            level++;
            addNode(node.left, result, level);
            addNode(node.right, result, level);
        }
    }
}
