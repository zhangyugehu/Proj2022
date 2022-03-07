package com.leetcode.q003;

import java.util.HashSet;
import java.util.Set;

public class WindowSolution implements ISolution {

    @Override
    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        if (s != null) {
            int len = 0;
            int l = 0;
            Set<Character> window = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                while (window.contains(s.charAt(i))) {
                    window.remove(s.charAt(l++));
                    len --;
                }
                len ++;
                window.add(s.charAt(i));
                max = Math.max(max, len);
            }
        }
        return max;
    }
}
