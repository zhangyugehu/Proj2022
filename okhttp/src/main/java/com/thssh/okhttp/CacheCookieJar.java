package com.thssh.okhttp;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.utils.SafeObjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author hutianhang
 */
public class CacheCookieJar implements CookieJar {
    ConcurrentHashMap<String, List<Cookie>> cache;

    public CacheCookieJar() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cache.put(url.host(), cookies);
        L.td("saveFromResponse", url.host(), Arrays.toString(cookies.toArray()));
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = SafeObjects.getOrDefault(this.cache.get(url.host()), Collections.emptyList());
        L.d("loadForRequest", url.host(), Arrays.toString(cookies.toArray()));
        return cookies;
    }
}
