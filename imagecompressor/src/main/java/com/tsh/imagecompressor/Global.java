package com.tsh.imagecompressor;

import android.text.format.Formatter;

import androidx.annotation.NonNull;

public class Global {
    public static long cost(@NonNull Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }

    public static String readableSize(long size) {
        return Formatter.formatFileSize(App.getInstance(), size);
    }
}
