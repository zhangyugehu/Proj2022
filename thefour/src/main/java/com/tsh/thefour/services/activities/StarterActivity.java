package com.tsh.thefour.services.activities;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thssh.commonlib.logger.L;
import com.tsh.thefour.R;

import java.util.Objects;

public abstract class StarterActivity extends AppCompatActivity implements L.Logger, ServiceConnection {

    TextView mConsoleLabel;
    ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        mConsoleLabel = findViewById(R.id.txt_console);
//        L.setLogger(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("MessengerServiceStarter");
    }

    public void onBindService(View view) {
        if (isBound()) {
            L.d("ALREADY bind service");
        } else bindService(getServiceIntent(), (conn = this), Context.BIND_AUTO_CREATE);
    }

    protected abstract boolean isBound();

    public void onUnBindService(View view) {
        if (isBound() && conn != null) {
            unbindService(conn);
        } else {
            L.d("NOT bind service yet.");
        }
    }

    public void onStartService(View view) {
        startService(getServiceIntent());
    }

    protected abstract Intent getServiceIntent();

    public void onStopService(View view) {
        stopService(getServiceIntent());
    }

    public abstract void onCallService(View view);

    @Override
    public void d(String tag, String msg) {
        mConsoleLabel.append(tag);
        mConsoleLabel.append(": ");
        mConsoleLabel.append(msg);
        mConsoleLabel.append("\n");
        Log.d(tag, msg);
    }

    @Override
    protected void onDestroy() {
        L.resetLogger();
        super.onDestroy();
    }

}
