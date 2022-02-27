package com.leetcode.q002;


public class Solution implements ISolution{

    @Override
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode it = null;
        ListNode root = null;
        ListNode one = l1, two = l2;
        boolean _add = false;
        while (one != null || two != null) {
            int oneValue = 0;
            if (one != null) {
                oneValue = one.val;
            }
            int twoValue = 0;
            if (two != null) {
                twoValue = two.val;
            }
            int added = oneValue + twoValue;
            if (_add) {
                added += 1;
                _add = false;
            }
            if (added > 9) {
                added %= 10;
                _add = true;
            }
            ListNode node = new ListNode();
            node.val = added;
            if (it == null) {
                root = it = node;
            } else {
                it.next = node;
                it = it.next;
            }

            if (one != null) one = one.next;
            if (two != null) two = two.next;
        }
        if (_add) {
            ListNode node = new ListNode();
            node.val = 1;
            it.next = node;
        }
        return root;
    }
}
