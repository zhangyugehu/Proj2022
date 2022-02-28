package com.thssh.activitygc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.thssh.activitygc.fragment.TransitionFragment;
import com.thssh.commonlib.activity.TrojanActivity;
import com.thssh.commonlib.logger.AbsLifeCycleActivity;

public class TransitionActivity extends AbsLifeCycleActivity {

    private static final String EXTRA_MSG = "extra_message";

    public static void open(Activity from, String msg) {
        Intent intent = new Intent(from, TransitionActivity.class);
        intent.putExtra(EXTRA_MSG, msg);
        from.startActivity(intent);
    }

    private String message;
    private TextView msgLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (restoreIntent()) {
            inCreate();
        } else {
            finish();
        }
    }

    private void inCreate() {
        setContentView(R.layout.activity_transition);
        msgLabel = findViewById(R.id.label_msg);
        msgLabel.setText(message);
    }

    private boolean restoreIntent() {
        if (getIntent() != null) {
            if (getIntent().hasExtra(EXTRA_MSG)) {
                message = getIntent().getStringExtra(EXTRA_MSG);
                return !TextUtils.isEmpty(message);
            }
        }
        return false;
    }

    public void startSingleInstance(View view) {
        startActivity(new Intent(this, SingleInstanceActivity.class));
    }

    public void startMemAlloc(View view) {
        startActivity(new Intent(this, MemAllocActivity.class));
    }

    public void startSelf(View view) {
        open(this, message + "[loop]");
    }

    public void startFragment(View view) {
        TrojanActivity.withFragment(this, TransitionFragment.class, TransitionFragment.newArgs("TransitionActivity"));
    }
}