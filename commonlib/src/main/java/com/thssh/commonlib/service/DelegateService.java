package com.thssh.commonlib.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.thssh.commonlib.interfaces.IServiceDelegate;
import com.thssh.commonlib.logger.ServiceLifeCycleDelegate;

import java.util.HashSet;
import java.util.Set;

public class DelegateService extends Service {
    private final Set<IServiceDelegate> mDelegate = new HashSet<>();

    public DelegateService() {
        register(new ServiceLifeCycleDelegate(getClass().getSimpleName() + "@" + hashCode()));
    }

    public void register(IServiceDelegate delegate) {
        mDelegate.add(delegate);
    }

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        for (IServiceDelegate delegate : mDelegate) {
            delegate.onCreate();
        }
    }

    @CallSuper
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for (IServiceDelegate delegate : mDelegate) {
            delegate.onStartCommand(intent, flags, startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        for (IServiceDelegate de : mDelegate) {
            de.onDestroy();
        }
    }

    @CallSuper
    @Override
    public boolean onUnbind(Intent intent) {
        for (IServiceDelegate de : mDelegate) {
            de.onUnbind(intent);
        }
        return super.onUnbind(intent);
    }

    @CallSuper
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        for (IServiceDelegate de : mDelegate) {
            de.onRebind(intent);
        }
    }

    @CallSuper
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        for (IServiceDelegate de : mDelegate) {
            de.onBind(intent);
        }
        return null;
    }
}
