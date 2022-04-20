package com.thssh.commonlib.utils;

public interface IFunc {
    interface Func<T> {
        void invoke(T param);
    }

    interface FuncR<T, R> {
        R invoke(T param);
    }
}
