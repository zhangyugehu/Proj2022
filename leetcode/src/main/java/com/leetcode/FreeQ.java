package com.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreeQ {

    public static void main(String[] args) {

        // maxDiff(args);

        // pairMain(args);

    }

    // region max range

    public static void maxRange(String[] args) {
        System.out.println(maxDiff(new String[] {"a", "b", "c", "b", "a", "d", "d", "d"}));
        System.out.println(maxDiff("babcabbbbbb"));
        System.out.println(maxDiff("bbbb"));
        System.out.println(maxDiff("abcacbbb"));
    }

    private static <T> int maxDiff(T[] in) {
        if (in == null || in.length < 1) {
            return 0;
        }
        int max = 0;
        int len = 0;
        int left = 0;
        Set<T> window = new HashSet<>();
        for (T t : in) {
            len++;
            while (window.contains(t)) {
                window.remove(in[left++]);
                len--;
            }
            window.add(t);
            max = Math.max(max, len);
        }
        return max;
    }

    private static int maxDiff(String in) {
        if (in == null || in.length() < 1) {
            return 0;
        }
        int max = 0;
        int len = 0;
        int left = 0;
        Set<Character> window = new HashSet<>();
        for (char t : in.toCharArray()) {
            len++;
            while (window.contains(t)) {
                window.remove(in.charAt(left++));
                len--;
            }
            window.add(t);
            max = Math.max(max, len);
        }
        return max;
    }
    //endregion

    //region pair

    public static void pairMain(String[] args) {
        System.out.println(pair(3));
    }

    private static List<String> pair(int n) {
        List<String> result = new ArrayList<>();
        _pair(result, 0, 0, n, "");
        return result;
    }

    private static void _pairAll(List<String> result, int l, int r, int n, String once) {
        if (l + r == n << 1) {
            if (l == n && r == n && once.startsWith("(") && once.endsWith(")")) {
                result.add(0, "✨️" + once);
            }
            else {
                result.add("❌" + once);
            }
        }
        else {
            _pair(result, l + 1, r, n, once + "(");
            _pair(result, l, r + 1, n, once + ")");
        }
    }

    private static void _pair(List<String> result, int l, int r, int n, String once) {
        if (l == n && r == n) {
            result.add(once);
        }
        if (l < n) {
            _pair(result, l + 1, r, n, once + "(");
        }
        if (r < n && r < l) {
            _pair(result, l, r + 1, n, once + ")");
        }
    }
    //endregion

}
