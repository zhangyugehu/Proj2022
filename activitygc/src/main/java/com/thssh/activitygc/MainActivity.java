package com.thssh.activitygc;

import android.os.Bundle;
import android.view.View;

import com.thssh.commonlib.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTransition(View view) {
        TransitionActivity.open(this, "MainActivity");
    }
}