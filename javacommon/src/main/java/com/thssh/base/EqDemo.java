package com.thssh.base;

public class EqDemo {
    public static void main(String[] args) {
        // 1,2,3,4,5,6,7,8
        // !(a == 1 || a==2) && !(a == 3 || a ==4)

        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + bingo(i));
        }
    }

    private static boolean bingo(int a) {
//        return (!(a == 1 || a==2) && !(a == 3 || a ==4));
        return !isEqualOr(a, 1, 2, 3, 4);
    }


    public static boolean isEqualOr(int target, int... srcs) {
        boolean result = false;
        for (int src : srcs) {
            if (src == target) {
                result = true;
                break;
            }
        }
        return result;
    }
}
