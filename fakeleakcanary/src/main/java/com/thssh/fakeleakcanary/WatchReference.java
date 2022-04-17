package com.thssh.fakeleakcanary;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Queue;

public class WatchReference<T> extends WeakReference<T> {
    String key;
    String desc;

    public WatchReference(String key, T watchTarget, String desc, ReferenceQueue<T> queue) {
        super(watchTarget, queue);
        this.key = key;
        this.desc = desc;
    }
}
