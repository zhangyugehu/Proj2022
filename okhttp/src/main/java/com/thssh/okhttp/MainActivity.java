package com.thssh.okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.thssh.commonlib.activity.BaseActivity;
import com.thssh.commonlib.executor.Executors;
import com.thssh.commonlib.executor.MainExecutor;
import com.thssh.commonlib.executor.ThreadChecker;
import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.views.LoadingWrapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

        try {
            LoadingWrapper.with(hello).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        start = System.currentTimeMillis();
        Executors.single().execute(() -> {
            Call call = OkClient.INSTANCE.getClient(60).newCall(new Request.Builder()
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