package com.thssh.proj2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.timer.IntervalTimer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testIntervalTimer();
    }

    IntervalTimer timer;

    private void testIntervalTimer() {
        IntervalTimer.with(this, 1_000, () -> L.td("with doWork!! " + System.currentTimeMillis()));
//        timer = new IntervalTimer(3_000, () -> L.td("doWork!! " + System.currentTimeMillis())).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IntervalTimer.pause(timer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntervalTimer.resume(timer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntervalTimer.destroy(timer);
    }

    public void openSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}