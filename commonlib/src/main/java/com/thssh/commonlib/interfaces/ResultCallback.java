package com.thssh.commonlib.interfaces;

public interface ResultCallback<T, R> {
    R invoke(T t);
}
