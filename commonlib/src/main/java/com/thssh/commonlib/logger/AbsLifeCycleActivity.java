package com.thssh.commonlib.logger;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AbsLifeCycleActivity extends AppCompatActivity {

    protected String logTag() {
        return getClass().getSimpleName() + "@" + hashCode();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(logTag(), "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d(logTag(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(logTag(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(logTag(), "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.d(logTag(), "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d(logTag(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(logTag(), "onDestroy");
    }
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
//        L.d(logTag(), "attachBaseContext");
//    }
//
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        L.d(logTag(), "onPostCreate");
//    }
//
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        L.d(logTag(), "onPostResume");
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        L.d(logTag(), "onRestoreInstanceState");
//    }
//
//    @Override
//    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onRestoreInstanceState(savedInstanceState, persistentState);
//        L.d(logTag(), "onRestoreInstanceState");
//    }
//
//    @Override
//    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onPostCreate(savedInstanceState, persistentState);
//        L.d(logTag(), "onPostCreate");
//    }
//
//    @Override
//    public void onTopResumedActivityChanged(boolean isTopResumedActivity) {
//        super.onTopResumedActivityChanged(isTopResumedActivity);
//        L.d(logTag(), "onTopResumedActivityChanged", isTopResumedActivity);
//    }
//
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        L.d(logTag(), "onAttachedToWindow");
//    }
//
//    @Override
//    public void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        L.d(logTag(), "onDetachedFromWindow");
//    }
}
