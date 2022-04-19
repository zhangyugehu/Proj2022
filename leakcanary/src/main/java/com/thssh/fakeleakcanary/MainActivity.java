package com.thssh.fakeleakcanary;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSwitchLoopedActivity(View view) {
        startActivity(new Intent(this, LoopedActivity.class));
    }

    public void onSwitchLeakedActivity(View view) {
        startActivity(new Intent(this, LeakedActivity.class));
    }
}