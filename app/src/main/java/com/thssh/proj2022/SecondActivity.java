package com.thssh.proj2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.View;

import com.thssh.commonlib.activity.TrojanActivity;
import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.timer.IntervalTimer;

import java.io.Serializable;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private IntervalTimer intervalTimer;
    private IntervalTimer intervalTimer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        intervalTimer = IntervalTimer.with(this, 3_000, this::onInterval);
        intervalTimer1 = IntervalTimer.with(this, 2_000, this::onInterval1);

        findViewById(R.id.button).setOnClickListener(this);

        Intent intent = getIntent();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
//            SerializableBean serializable = (SerializableBean) intent.getSerializableExtra("serializable" + i);
            ParcelableBean parcelable = intent.getParcelableExtra("parcelable");
        }
        L.d("duration:", System.nanoTime() - start);
//        L.d("serializable", serializable.count);
//        L.d("parcelable", parcelable.count);
    }

    private void onInterval1() {
        L.td("onInterval2 START", SystemClock.uptimeMillis());
        IntervalTimer.destroy(intervalTimer1);
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                L.td("onInterval2 COMPLETED", SystemClock.uptimeMillis());
                IntervalTimer.resume(intervalTimer1);
            }
        }.start();
    }

    private void onInterval() {
        L.td("onInterval START", SystemClock.uptimeMillis());
        IntervalTimer.destroy(intervalTimer);
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                L.td("onInterval COMPLETED", SystemClock.uptimeMillis());
                IntervalTimer.resume(intervalTimer);
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        TrojanActivity.withFragment(this, MainFragment.class, null);
    }
}