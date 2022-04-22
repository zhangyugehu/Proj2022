package com.tsh.thefour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tsh.thefour.services.activities.BinderStarterActivity;
import com.tsh.thefour.services.activities.MessengerStarterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, BinderStarterActivity.class));
//        startActivity(new Intent(this, MessengerStarterActivity.class));
        finish();
    }
}