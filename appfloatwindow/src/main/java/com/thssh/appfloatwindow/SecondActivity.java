package com.thssh.appfloatwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thssh.commonlib.logger.L;

public class SecondActivity extends PopWindowActivity {
    public SecondActivity() {
        L.d("SecondActivity", L.getStackTracesPlain(20));
    }

    PopWindowService popService;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = new Intent(this, PopWindowService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service instanceof PopWindowService.InnerBinder) {
                    popService = ((PopWindowService.InnerBinder) service).getService();
                    L.d("SecondActivity", "onServiceConnected: ", popService.hashCode());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);

        timer = new CountDownTimer(50000, 500) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (popService != null) {
                    popService.textView.setText("tick: " + millisUntilFinished);
                    popService.textView.setTextColor((int) (millisUntilFinished * Color.RED));
                    updateWindow();
                }
            }

            @Override
            public void onFinish() {
                if (popService != null) {
                    popService.textView.setText("finished.");
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showWindowWhenConnected();
        // 14, 9, 78, 78, 81, 72, 61, 64
        // 14, 9, 61, 64, 78, 81, 78, 78
    }

    void showWindowWhenConnected() {
        if (popService != null) {
            L.d("showWindow");
//            showWindow(popService.getRootView(this));
            popService.showWindow(this);
        } else {
            getWindow().getDecorView().postDelayed(this::showWindowWhenConnected, 500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}