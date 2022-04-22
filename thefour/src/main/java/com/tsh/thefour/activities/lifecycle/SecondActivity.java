package com.tsh.thefour.activities.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thssh.commonlib.activity.BaseActivity;
import com.tsh.thefour.R;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onStartA(View view) {
        startActivity(new Intent(this, FirstActivity.class));
    }
}