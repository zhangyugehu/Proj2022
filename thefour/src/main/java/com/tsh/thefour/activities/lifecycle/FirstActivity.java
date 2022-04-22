package com.tsh.thefour.activities.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thssh.commonlib.activity.BaseActivity;
import com.tsh.thefour.R;

public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void onStartB(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}