package com.leetcode.q102;

import org.w3c.dom.Node;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * 102. 二叉树的层序遍历<br>
 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
 *<br><br><br>
 *
 *
 * 示例 1：<br>
 *<br><br>
 *
 * 输入：root = [3,9,20,null,null,15,7]<br>
 * 输出：[[3],[9,20],[15,7]]<br>
 * 示例 2：<br>
 *<br>
 * 输入：root = [1]<br>
 * 输出：[[1]]<br>
 * 示例 3：<br>
 *<br>
 * 输入：root = []<br>
 * 输出：[]<br>
 *<br>
 *<br>
 * 提示：<br>
 *<br>
 * 树中节点数目在范围 [0, 2000] 内
 * -1000 <= Node.val <= 1000
 */
public class Q102 {

    public static void main(String[] args) {
//        Solution solution = new QueueSolution();
        Solution solution = new StackSolution();
        printResult(solution.levelOrder(createParams(3, 9, 20, null, null, 15, 7)));
    }

    private static void printResult(List<List<Integer>> levelOrder) {
        System.out.println("[");
        for (List<Integer> level: levelOrder){
            System.out.print("\t[");
            for (Integer integer : level) {
                System.out.print(integer + ", ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }

    private static TreeNode createParams(Integer... leafs) {
        TreeNode root = new TreeNode();
        root.val = leafs[0];
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        for (int i = 1; i < leafs.length;) {
            TreeNode node = queue.poll();
            if (node != null) {
                Integer leftVal = leafs[i++];
                if (leftVal != null) {
                    TreeNode left = new TreeNode();
                    left.val = leftVal;
                    node.left = left;
                    queue.add(left);
                }
                Integer rightVal = leafs[i++];
                if (rightVal != null) {
                    TreeNode right = new TreeNode();
                    right.val = rightVal;
                    node.right = right;
                    queue.add(right);
                }
            }
        }
        return root;
    }
}
