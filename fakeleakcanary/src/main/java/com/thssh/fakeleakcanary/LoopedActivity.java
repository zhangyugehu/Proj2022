package com.thssh.fakeleakcanary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoopedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looped);
    }

    public void loopOpenSelf(View view) {
        startActivity(new Intent(this, LoopedActivity.class));
    }

    public void openLeakedActivity(View view) {
        startActivity(new Intent(this, LeakedActivity.class));
    }

    public void openMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}