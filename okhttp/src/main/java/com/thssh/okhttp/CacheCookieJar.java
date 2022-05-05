package com.thssh.okhttp;

import androidx.annotation.NonNull;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.utils.SafeObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author hutianhang
 */
public class CacheCookieJar implements CookieJar {
    HashMap<String, List<Cookie>> cache;

    List<Cookie> c1 = new ArrayList<>();
    List<Cookie> c2 = new ArrayList<>();
    List<Cookie> c3 = new ArrayList<>();
    List<List<Cookie>> combo = new ArrayList<>();

    Random r = new Random();

    public CacheCookieJar() {
        cache = new HashMap<>();
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
//        cache.put(url.host(), cookies);
//        cookies.addAll(combo.get(r.nextInt(3)));
//        if (r.nextInt() % 2 == 0) {
//            cookies.add(new Cookie.Builder()
//                    .name("name-")
//                    .domain("domain-")
//                    .value("value-")
//                    .expiresAt(System.currentTimeMillis() + 100000)
//                    .path("/")
//                    .build());
//        } else {
//            cookies.clear();
//        }
        L.td("saveFromResponse", url.host(), Arrays.toString(cookies.toArray()));
    }

    {
        Collections.addAll(combo, c1, c2, c3);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < r.nextInt(10); i++) {
                combo.get(j).add(new Cookie.Builder()
                        .name("name-" + i)
                        .domain("domain-" + i)
                        .value("value-" + i)
                        .expiresAt(System.currentTimeMillis() + 100000)
                        .path("/")
                        .build());
            }
        }
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = combo.get(r.nextInt(3));//SafeObjects.getOrDefault(this.cache.get(url.host()), Collections.emptyList());
        L.d("loadForRequest", url.host(), Arrays.toString(cookies.toArray()));
        return cookies;
    }
}
