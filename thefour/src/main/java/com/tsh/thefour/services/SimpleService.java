package com.tsh.thefour.services;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.service.BaseService;

import java.util.Random;

public class SimpleService extends BaseService implements Handler.Callback {
    public static class Const {
        public static final int WHAT_REQUEST_SERVICE_METHOD = 703;
        public static final int WHAT_REPAY_SERVICE_METHOD = 570;
    }

    private static final int WHAT_PRINT_ALIVE = 868;
    private static final int ALIVE_CHECK_DELAY = 3_000;

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == WHAT_PRINT_ALIVE) {
            printAlive();
            return true;
        }
        return false;
    }

    long createTime;
    Handler handler;
    final Random mGenerator = new Random();

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper(), this);
        createTime = SystemClock.uptimeMillis();
        printAlive();
    }

    private void printAlive() {
        L.d("service alive", (SystemClock.uptimeMillis() - createTime) / 1000 + "s");
        handler.sendEmptyMessageDelayed(WHAT_PRINT_ALIVE, ALIVE_CHECK_DELAY);
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * fake call Service method
     * @return
     */
    public int getRandomNumber() {
        return mGenerator.nextInt();
    }
}
