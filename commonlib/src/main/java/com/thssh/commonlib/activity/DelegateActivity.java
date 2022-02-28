package com.thssh.commonlib.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thssh.commonlib.interfaces.IActivityDelegate;
import com.thssh.commonlib.logger.ActivityLifeCycleDelegate;

import java.util.ArrayList;
import java.util.List;

public abstract class DelegateActivity extends AppCompatActivity {
    private final List<IActivityDelegate> mActivityDelegate;

    public DelegateActivity() {
        mActivityDelegate = new ArrayList<>();
        registerActivityDelegate(new ActivityLifeCycleDelegate(getClass().getSimpleName() + "@" + hashCode()));
    }

    public void registerActivityDelegate(IActivityDelegate delegate) {
        if (!mActivityDelegate.contains(delegate)) {
            mActivityDelegate.add(delegate);
        }
    }

    public void unregisterActivityDelegate(IActivityDelegate delegate) {
        mActivityDelegate.remove(delegate);
    }

    public void setCenterTitleView(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (IActivityDelegate delegate : mActivityDelegate) {
            delegate.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (IActivityDelegate delegate : mActivityDelegate) {
            delegate.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (IActivityDelegate delegate : mActivityDelegate) {
            delegate.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (IActivityDelegate delegate : mActivityDelegate) {
            delegate.onPause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (IActivityDelegate delegate : mActivityDelegate) {
            delegate.onRestart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (IActivityDelegate delegate : mActivityDelegate) {
            delegate.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (IActivityDelegate delegate : mActivityDelegate) {
            delegate.onDestroy();
        }
        mActivityDelegate.clear();
    }
}
