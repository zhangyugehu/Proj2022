package com.thssh.okhttp;

import android.util.SparseArray;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author hutianhang
 */

public enum OkClient {
    /**
     * singleton
     */
    INSTANCE;

    private OkHttpClient baseClient;

    private SparseArray<OkHttpClient> timeoutClients;

    OkClient() {
        timeoutClients = new SparseArray<>();
    }

    private OkHttpClient getBaseClient() {
        if (baseClient == null) {
            baseClient = new OkHttpClient.Builder().build();
        }
        return baseClient;
    }

    public OkHttpClient getClient(int timeout) {
        return timeoutClients.get(timeout, getBaseClient().newBuilder()
                .callTimeout(timeout, TimeUnit.SECONDS)
                .build());
    }

    public OkHttpClient getDefault() {
        return getClient(5);
    }
}
