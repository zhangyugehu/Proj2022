package com.thssh.commonlib.activity;

import com.thssh.commonlib.logger.AbsLifeCycleActivity;

public abstract class BaseActivity extends AbsLifeCycleActivity {

    protected void setCenterTitleView(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
