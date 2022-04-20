package com.thssh.commonlib.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hutianhang
 */
public class SafeObjects {

    public static <P> void safeCallback(P obj, IFunc.Func<P> func) {
        if (obj != null) {
            func.invoke(obj);
        }
    }

    @NonNull
    public static <T> T getOrDefault(@Nullable T value, @NonNull T defValue) {
        if (value == null) {
            return defValue;
        }
        return value;
    }

    @SafeVarargs
    @NonNull
    public static <T> T getOrDefault(@Nullable T value, @NonNull T defValue, T... unExcepts) {
        if (value == null) {
            return defValue;
        }
        for (T unExcept : unExcepts) {
            if (value == unExcept || value.equals(unExcept)) {
                return defValue;
            }
        }
        return value;
    }

    @SafeVarargs
    public static <T> boolean equalsAny(T target, T... srcs) {
        for (T src : srcs) {
            if (src.equals(target)) {
                return true;
            }
        }
        return false;
    }

    @SafeVarargs
    public static <T> boolean isPass(IFunc.FuncR<T, Boolean> func, T... args) {
        for (T arg : args) {
            if (!func.invoke(arg)) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public static <T> boolean isNotPass(IFunc.FuncR<T, Boolean> func, T... args) {
        for (T arg : args) {
            if (func.invoke(arg)) {
                return true;
            }
        }
        return false;
    }
}
