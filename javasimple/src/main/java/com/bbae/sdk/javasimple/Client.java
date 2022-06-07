package com.bbae.sdk.javasimple;

import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) {

    }

    public static void main1(String[] args) {
        long start = System.nanoTime();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            runCode();
        }
        System.out.println("dur: " + (System.nanoTime() - start) / count + "ns");
    }

    private static void runCode() {
        int count = 1000;
        int size = (int) ((count / 0.75) + 1);
        // + 355199ns 389763ns 375851ns 375419ns 363684ns 372559ns
        // - 465906ns 470116ns 475871ns
        // * 395591ns 375958ns 379318ns
        Map<String, String> map = new HashMap<>(size);
        for (int i = 0; i < count; i++) {
            map.put("index-" + i, "value-" + i);
        }
    }
}