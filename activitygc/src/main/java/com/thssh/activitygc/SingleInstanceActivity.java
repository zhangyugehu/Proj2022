package com.thssh.activitygc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thssh.activitygc.fragment.TransitionFragment;
import com.thssh.commonlib.activity.TrojanActivity;
import com.thssh.commonlib.logger.AbsLifeCycleActivity;

public class SingleInstanceActivity extends AbsLifeCycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
    }

    public void startTransition(View view) {
        TransitionActivity.open(this, "SingleInstanceActivity");
    }

    public void startFragment(View view) {
        TrojanActivity.withFragment(this, TransitionFragment.class, TransitionFragment.newArgs("TransitionActivity"));
    }
}