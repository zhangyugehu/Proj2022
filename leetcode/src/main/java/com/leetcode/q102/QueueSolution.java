package com.leetcode.q102;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class QueueSolution implements Solution {
    @Override
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);

        while (!deque.isEmpty()) {
            int takeSize = deque.size();
            List<Integer> levelList = new ArrayList<>(takeSize);
            for (int i = 0; i < takeSize; i++) {
                TreeNode first = deque.poll();
                if (first != null) {
                    levelList.add(first.val);
                    if (first.left != null) deque.offer(first.left);
                    if (first.right != null) deque.offer(first.right);
                }
            }
            result.add(levelList);
        }
        return result;
    }
}
