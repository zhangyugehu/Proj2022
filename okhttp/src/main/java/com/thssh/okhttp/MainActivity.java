package com.thssh.okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.thssh.commonlib.activity.BaseActivity;
import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.views.LoadingWrapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        Call call = OkClient.INSTANCE.getClient(60).newCall(new Request.Builder()
                .url("https://api.bbaecache.com/api/v2/market/getAllSymbols")
                .get().build());
        call.enqueue(this);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        L.d("onFailure: ",
                e.getMessage(),
                String.valueOf((System.currentTimeMillis() - start) / 1000));
        hello.post(() -> {
            hello.setText(e.getMessage());
            LoadingWrapper.with(hello).dismiss();
        });
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
        String finalBodyStringify = bodyStringify;
        hello.post(() -> {
            LoadingWrapper.with(hello).dismiss();
            hello.setText(finalBodyStringify);
        });
    }
}