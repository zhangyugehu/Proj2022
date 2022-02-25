package com.thssh.launchmode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SingleInstanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
    }
}