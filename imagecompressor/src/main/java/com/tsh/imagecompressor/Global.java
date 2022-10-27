package com.tsh.imagecompressor;

import androidx.annotation.NonNull;

public class Global {
    public static long cost(@NonNull Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }
}
