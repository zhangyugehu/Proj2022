package com.thssh.okhttp;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import com.thssh.commonlib.activity.BaseActivity;
import com.thssh.commonlib.executor.Executors;
import com.thssh.commonlib.executor.ThreadChecker;
import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.views.LoadingWrapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.AsyncTimeout;

/**
 * @author hutianhang
 */
public class MainActivity extends BaseActivity implements Callback {

    TextView hello;
    long start = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = findViewById(R.id.hello);

//        normalRequest();

//        traceRequest();

        for (int i = 0; i < 100; i++) {
            multiThreadHeaderModify();
        }

    }

    private static final String GITHUB_ZEN = "https://api.github.com/zen";
    private static final String QUOTE_INFO = "https://transformer-web--develop-02.bbaecache.com/api/v2/market/getQuoteInfo";

    private void multiThreadHeaderModify() {
        Request.Builder requestBuilder = new Request.Builder();
        Request trace = requestBuilder.url(QUOTE_INFO).build();
        OkClient.getClient(60).newCall(trace).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                L.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                L.d("onResponse", response.body().string());
            }
        });
    }

    private void traceRequest() {
        Request.Builder requestBuilder = new Request.Builder();
        Request trace = requestBuilder.method("TRACE", null).url("https://bbaecache.com").tag("tag-trace").build();
        OkClient.getClient(60).newCall(trace).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                L.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                L.d("onResponse", response.body().string());
            }
        });
    }

    private void normalRequest() {
        try {
            LoadingWrapper.with(hello).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        start = System.currentTimeMillis();
        Executors.single().execute(() -> {
            Call call = OkClient.getClient(60).newCall(new Request.Builder()
                    .url("https://www.google.com")
                    .get().build());
            try {
                Response response = call.execute();
                L.d("Response", response.code());
                final String stringifyBody = response.body().string();
                setHelloText(stringifyBody);
            } catch (IOException e) {
                L.d("IOException", e.getMessage());
            }
//            call.enqueue(this);
        });

        AsyncTimeout timeout = new AsyncTimeout() {

            @Override
            protected void timedOut() {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    L.td("timedOut");
                }
            }
        };
        timeout.timeout(2, TimeUnit.SECONDS);
        timeout.enter();
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        L.d("onFailure: ",
                e.getMessage(),
                String.valueOf((System.currentTimeMillis() - start) / 1000));
        setHelloText(e.getMessage());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {

        String bodyStringify = "empty body";
        try {
            if (response.body() != null) {
                bodyStringify = response.body().string();
            }
            L.td("onResponse: ",
                    response.message(),
                    bodyStringify,
                    String.valueOf((System.currentTimeMillis() - start) / 1000));
        } catch (IOException e) {
            bodyStringify = e.getMessage();
        }
        setHelloText(bodyStringify);
    }

    private void setHelloTextDirect(CharSequence text) {
        LoadingWrapper.release(hello);
        hello.setText(text);
    }
    private void setHelloText(CharSequence text) {
        if (ThreadChecker.isUIThread()) {
            setHelloTextDirect(text);
        } else {
            Executors.main().execute(text, this::setHelloTextDirect);
        }
    }
}