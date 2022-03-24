package com.thssh.customview.helper;

/**
 * @author hutianhang
 */
public interface IRepo<T> {

    void save(T data);

    T restore();
}
