package com.thssh.commonlib.interfaces;

import android.content.Intent;

public interface IServiceDelegate {
    void onCreate();
    void onStartCommand(Intent intent, int flags, int startId);
    void onDestroy();
    void onUnbind(Intent intent);
    void onRebind(Intent intent);
    void onBind(Intent intent);
}
