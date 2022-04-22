package com.tsh.thefour.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import com.thssh.commonlib.logger.L;

public class MessengerService extends SimpleService {

    static class IncomingHandler extends Handler {
        Context appContext;

        public IncomingHandler(Context appContext) {
            super(Looper.getMainLooper());
            this.appContext = appContext;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
        }
    }

    Messenger messenger;

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
//        messenger = new Messenger(new IncomingHandler(this));
        messenger = new Messenger(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == Const.WHAT_REQUEST_SERVICE_METHOD) {
                    Message message = Message.obtain();
                    message.what = Const.WHAT_REPAY_SERVICE_METHOD;
                    message.arg1 = getRandomNumber();
                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        L.d("MessengerService", "RemoteException", e.getMessage());
                    }
                }
            }
        });
        return messenger.getBinder();
    }
}