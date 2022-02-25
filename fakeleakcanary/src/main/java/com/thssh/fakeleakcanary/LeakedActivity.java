package com.thssh.fakeleakcanary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public class LeakedActivity extends AppCompatActivity {

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                Log.i(App.TAG, "handleMessage: here!!!");
            } else {
                Log.i(App.TAG, "handleMessage: don't care msg. " + msg.what);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaked);
        new Thread(() -> {
            mHandler.post(() -> {
                if (BuildConfig.DEBUG) Log.d(App.TAG, "post Runnable: ");
            });
            SystemClock.sleep(2000);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }).start();
    }

    public void openLoopActivity(View view) {
        startActivity(new Intent(this, LoopedActivity.class));
    }
}