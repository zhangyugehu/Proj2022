package com.tsh.thefour.services.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

import com.thssh.commonlib.logger.L;
import com.tsh.thefour.services.BinderService;

public class BinderStarterActivity extends StarterActivity {

    BinderService service;

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        if (binder instanceof BinderService.InnerBinder) {
            service = ((BinderService.InnerBinder) binder).getService();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        L.d("onServiceDisconnected", name);
        service = null;
    }

    @Override
    public void onUnBindService(View view) {
        super.onUnBindService(view);
        service = null;
    }

    @Override
    protected boolean isBound() {
        return service != null;
    }

    @Override
    protected Intent getServiceIntent() {
        return new Intent(this, BinderService.class);
    }

    @Override
    public void onCallService(View view) {
        if (service == null) {
            L.d("BinderStarterActivity", "NOT bind service yet.");
        } else {
            int randomNumber = service.getRandomNumber();
            L.d("BinderStarterActivity", "call service method", "getRandomNumber", randomNumber);
        }
    }
}