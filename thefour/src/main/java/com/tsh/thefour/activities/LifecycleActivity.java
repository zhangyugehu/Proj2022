package com.tsh.thefour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thssh.commonlib.logger.L;
import com.tsh.thefour.R;
import com.tsh.thefour.activities.lifecycle.FirstActivity;

import java.util.Objects;

public class LifecycleActivity extends AppCompatActivity implements L.Logger {

    @Override
    public void d(String tag, String msg) {
        mConsoleLabel.append(tag);
        mConsoleLabel.append(": ");
        mConsoleLabel.append(msg);
        mConsoleLabel.append("\n");
        Log.d(tag, msg);
    }

    TextView mConsoleLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Lifecycle");
        setContentView(R.layout.activity_lifecycle);
        mConsoleLabel = findViewById(R.id.txt_console);
        L.setLogger(this);
    }

    public void onStartA(View view) {
        startActivity(new Intent(this, FirstActivity.class));
    }

    @Override
    protected void onDestroy() {
        L.resetLogger();
        super.onDestroy();
    }

    public void onClean(View view) {
        mConsoleLabel.setText("");
    }
}