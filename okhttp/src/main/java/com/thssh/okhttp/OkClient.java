package com.thssh.okhttp;

import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.thssh.commonlib.logger.L;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author hutianhang
 */

public enum OkClient {
    /**
     * singleton
     */
    INSTANCE;

    private OkHttpClient baseClient;

    private final SparseArray<OkHttpClient> timeoutClients;

    OkClient() {
        timeoutClients = new SparseArray<>();
    }

    private OkHttpClient getBaseClient() {
        if (baseClient == null) {
            baseClient = new OkHttpClient.Builder().build();
        }
        return baseClient;
    }

    private static final Random sr = new Random();
    public static OkHttpClient getClient(int timeout) {
        return INSTANCE.timeoutClients.get(timeout, INSTANCE.getBaseClient()).newBuilder()
                .cookieJar(new CacheCookieJar())
                .addInterceptor(chain -> {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    for (int i = 0; i < sr.nextInt(10); i++) {
                        requestBuilder.addHeader("name" + i, "value" + i);
                    }
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }).addInterceptor(chain -> {
                    Request request = chain.request();
                    StringBuilder sb = new StringBuilder("[INTERCEPTOR]Header");
                    for (String name : request.headers().names()) {
                        sb.append(name).append("=").append(request.headers(name)).append("; ");
                    }
                    L.td(sb);
                    return chain.proceed(request);
                })
//                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("la.thssh.tech", 12316)))
//                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("97.64.18.128", 12316)))
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.166", 7890)))
                .callTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getDefault() {
        return getClient(5);
    }
}
