package com.leetcode.q003;

import java.util.Random;

/**
 * 3. 无重复字符的最长子串<br>
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。<br>
 *<br><br><br>
 *
 *
 * 示例 1:<br>
 *<br>
 * 输入: s = "abcabcbb"<br>
 * 输出: 3 <br>
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。<br>
 * 示例 2:<br>
 *<br>
 * 输入: s = "bbbbb"<br>
 * 输出: 1<br>
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。<br>
 * 示例 3:<br><br>
 *
 * 输入: s = "pwwkew"<br>
 * 输出: 3<br>
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。<br>
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。<br>
 *<br><br>
 *
 * 提示：<br>
 *<br>
 * 0 <= s.length <= 5 * 104
 * s 由英文字母、数字、符号和空格组成
 */
public class Q003 {

    public static void main(String[] args) {
        ISolution solution = new WindowSolution();
        String[] tests = new String[] {
                "abcabcbb",
                "bbbbb",
                "pwwkew"
        };

        for (String test : tests) {
            System.out.println(test + " >>> " + solution.lengthOfLongestSubstring(test));
        }

        Random random = new Random();
        int start = 32;
        int end = 125;
        char[] charSeq = new char[(5 * 104) + 1];
        for (int i = 0; i < charSeq.length; i++) {
            charSeq[i] = (char) (random.nextInt(end - start) + start);
        }
        String maxString = String.valueOf(charSeq);
        System.out.println(maxString + " >>> " + solution.lengthOfLongestSubstring(maxString));
        System.out.println(maxString.length());
    }
}
