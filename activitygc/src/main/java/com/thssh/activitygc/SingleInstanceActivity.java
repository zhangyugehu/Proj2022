package com.thssh.activitygc;

import android.os.Bundle;
import android.view.View;

import com.thssh.activitygc.fragment.TransitionFragment;
import com.thssh.commonlib.activity.BaseActivity;
import com.thssh.commonlib.activity.TrojanActivity;

public class SingleInstanceActivity extends BaseActivity {

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