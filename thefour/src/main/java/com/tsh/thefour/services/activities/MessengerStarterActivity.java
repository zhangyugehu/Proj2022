package com.tsh.thefour.services.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import androidx.annotation.NonNull;

import com.thssh.commonlib.logger.L;
import com.tsh.thefour.services.MessengerService;
import com.tsh.thefour.services.SimpleService;

public class MessengerStarterActivity extends StarterActivity {

    Messenger serverMessenger;
    Messenger clientMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientMessenger = new Messenger(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == SimpleService.Const.WHAT_REPAY_SERVICE_METHOD) {
                    L.d("MessengerStarterActivity", "call service method", "getRandomNumber", msg.arg1);
                }
            }
        });
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        serverMessenger = new Messenger(binder);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        serverMessenger = null;
        L.d("onServiceDisconnected", name);
    }

    @Override
    public void onUnBindService(View view) {
        super.onUnBindService(view);
        serverMessenger = null;
    }

    @Override
    protected boolean isBound() {
        return serverMessenger != null;
    }

    @Override
    protected Intent getServiceIntent() {
        return new Intent(this, MessengerService.class);
    }

    @Override
    public void onCallService(View view) {
        if (serverMessenger == null) {
            L.d("BinderStarterActivity", "NOT bind service yet.");
        } else {
            Message message = Message.obtain();
            message.replyTo = clientMessenger;
            message.what = SimpleService.Const.WHAT_REQUEST_SERVICE_METHOD;
            try {
                serverMessenger.send(message);
            } catch (RemoteException e) {
                L.d("RemoteException", e.getMessage());
            }
        }
    }
}