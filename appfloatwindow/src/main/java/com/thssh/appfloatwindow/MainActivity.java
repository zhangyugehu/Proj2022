package com.thssh.appfloatwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.utils.UI;

public class MainActivity extends AppCompatActivity {
    PopWindowService popService;
    PopView popView;

    public MainActivity() {
        L.d("MainActivity", L.getStackTracesPlain(8));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        popView = new PopView(this);

        Intent intent = new Intent(this, PopWindowService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service instanceof PopWindowService.InnerBinder) {
                    popService = ((PopWindowService.InnerBinder) service).getService();
                    L.d("MainActivity", "onServiceConnected: ", popService.hashCode());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showWindowWhenConnected();
    }

    void showWindowWhenConnected() {
        if (popService != null) {
            L.d("showWindow");
//            showWindow(popService.getRootView(this));
            popService.setContentView(popView);
            popService.showWindow(this);
        } else {
            getWindow().getDecorView().postDelayed(this::showWindowWhenConnected, 500);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (popService != null) {
            popService.hideWidow(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (popService != null && popService.getContentView() != null) {
            PopView popView = popService.getContentView();
            popService.hideWidow(this);
        }
    }

    public void startSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}