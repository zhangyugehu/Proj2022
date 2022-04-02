package com.thssh.okhttp;

import android.util.SparseArray;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
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
                .cookieJar(new CacheCookieJar())
//                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("la.thssh.tech", 12316)))
//                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("97.64.18.128", 12316)))
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.166", 7890)))
                .callTimeout(timeout, TimeUnit.SECONDS)
                .build());
    }

    public OkHttpClient getDefault() {
        return getClient(5);
    }
}
