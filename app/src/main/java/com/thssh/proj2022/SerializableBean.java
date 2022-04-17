package com.thssh.proj2022;

import java.io.Serializable;

/**
 * @author hutianhang
 */
public class SerializableBean implements Serializable {

    int count;

    public SerializableBean(int count) {
        this.count = count;
    }
}
