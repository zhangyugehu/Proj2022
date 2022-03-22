package com.thssh.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 测试:
 * ui线程和主线程不一定时同一个线程
 * 在子线程中初始化自己的Looper
 * @author hutianhang
 */
public class ThreadViewActivity extends AppCompatActivity {
    private static final String TAG = "ThreadViewTag";
    private TextView threadText, mainText;
    WindowManager.LayoutParams param;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_view);
        mainText = findViewById(R.id.main_text);
        threadText = new TextView(this);
        threadText.setText("ThreadView");
        threadText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
        threadText.setTextColor(Color.parseColor("#66ccff"));

        param = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION);
        param.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        param.width = WindowManager.LayoutParams.WRAP_CONTENT;
        param.height = WindowManager.LayoutParams.WRAP_CONTENT;
        param.gravity = Gravity.CENTER;
        param.format = PixelFormat.TRANSLUCENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            param.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        new Thread(this::threadView, "threadView").start();

        threadText.postDelayed(() -> {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "threadText postDelayed: " + Thread.currentThread().getName());
            }
        }, 2000);

        mainText.postDelayed(() -> {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "mainText postDelayed: " + Thread.currentThread().getName());
            }
        }, 2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2500);
                runOnUiThread(() -> {
                    Log.d(TAG, "runOnUiThread: " + Thread.currentThread().getName());
                    threadText.setText("change by runOnUiThread.");
//            mainText.setText("change by runOnUiThread.");
                });
            }
        }).start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    private void threadView() {
        Looper.prepare();
        getWindowManager().addView(threadText, param);
        Looper.loop();
    }
}