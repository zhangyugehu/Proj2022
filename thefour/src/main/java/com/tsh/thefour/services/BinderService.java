package com.tsh.thefour.services;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class BinderService extends SimpleService implements Handler.Callback {

    public class InnerBinder extends Binder {
        public BinderService getService() {
            return BinderService.this;
        }
    }

    InnerBinder binder;

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        if (binder != null) {
            return binder;
        }
        return (binder = new InnerBinder());
    }
}