package com.thssh.commonlib.logger;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.interfaces.IActivityDelegate;


public class ActivityLifeCycleDelegate implements IActivityDelegate {
    private final String tag;

    public ActivityLifeCycleDelegate(String tag) {
        this.tag = tag;
    }

    protected String logTag() {
        return tag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        L.d(logTag(), "onCreate");
    }

    @Override
    public void onStart() {
        L.d(logTag(), "onStart");
    }

    @Override
    public void onResume() {
        L.d(logTag(), "onResume");
    }

    @Override
    public void onPause() {
        L.d(logTag(), "onPause");
    }

    @Override
    public void onRestart() {
        L.d(logTag(), "onRestart");
    }

    @Override
    public void onStop() {
        L.d(logTag(), "onStop");
    }

    @Override
    public void onDestroy() {
        L.d(logTag(), "onDestroy");
    }

    @Override
    public void attachBaseContext(Context newBase) {
        L.d(logTag(), "attachBaseContext");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        L.d(logTag(), "onPostCreate");
    }

    @Override
    public void onPostResume() {
        L.d(logTag(), "onPostResume");
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        L.d(logTag(), "onRestoreInstanceState");
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        L.d(logTag(), "onRestoreInstanceState");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        L.d(logTag(), "onPostCreate");
    }

    @Override
    public void onTopResumedActivityChanged(boolean isTopResumedActivity) {
        L.d(logTag(), "onTopResumedActivityChanged", isTopResumedActivity);
    }

    @Override
    public void onAttachedToWindow() {
        L.d(logTag(), "onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        L.d(logTag(), "onDetachedFromWindow");
    }
}
