package com.thssh.commonlib.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hutianhang
 */
public class SafeObjects {
    @NonNull
    public static <T> T getOrDefault(@Nullable T value, @NonNull T defValue) {
        if (value == null) {
            return defValue;
        }
        return value;
    }

    public static <T> boolean equalsAny(T target, T... srcs) {
        for (T src : srcs) {
            if (src.equals(target)) {
                return true;
            }
        }
        return false;
    }
}
