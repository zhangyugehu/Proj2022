package com.thssh.commonlib.logger;

import android.content.Intent;

import com.thssh.commonlib.interfaces.IServiceDelegate;

public class ServiceLifeCycleDelegate implements IServiceDelegate {
    private String tag;

    public ServiceLifeCycleDelegate(String tag) {
        this.tag = tag;
    }

    protected String logTag() {
        return tag;
    }

    @Override
    public void onCreate() {
        L.d(logTag(), "onCreate");
    }

    @Override
    public void onStartCommand(Intent intent, int flags, int startId) {

        L.d(logTag(), "onStartCommand", flags, startId);
    }

    @Override
    public void onDestroy() {

        L.d(logTag(), "onDestroy");
    }

    @Override
    public void onUnbind(Intent intent) {

        L.d(logTag(), "onUnbind");
    }

    @Override
    public void onRebind(Intent intent) {

        L.d(logTag(), "onRebind");
    }

    @Override
    public void onBind(Intent intent) {

        L.d(logTag(), "onBind");
    }
}
