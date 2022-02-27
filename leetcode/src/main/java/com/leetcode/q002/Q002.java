package com.leetcode.q002;

public class Q002 {
    public static void main(String[] args) {
        ISolution solution = new Solution();
        printResult(solution.addTwoNumbers(
                createListNode(9,9,9,9,9,9,9),
                createListNode(9,9,9,9)
        ));

    }

    private static ListNode createListNode(int... args) {
        ListNode node = new ListNode();
        node.val = args[0];
        ListNode it = node;
        for (int i = 1; i < args.length; i++) {
            ListNode cur = new ListNode();
            cur.val = args[i];
            it.next = cur;
            it = cur;
        }
        return node;
    }

    private static void printResult(ListNode node) {
        ListNode it = node;
        while (it != null) {
            System.out.print(it.val + ", ");
            it = it.next;
        }
    }
}
