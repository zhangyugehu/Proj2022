package com.thssh.commonlib.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @author hutianhang
 */
public class MainExecutor extends ParamaterExecutor {
    
    Handler handler = new Handler(Looper.getMainLooper());
    
    @Override
    public void execute(Runnable command) {
        handler.post(command);
    }
}
