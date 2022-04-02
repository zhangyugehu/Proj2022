package com.thssh.commonlib.executor;

import java.util.concurrent.Executor;

public class Executors {

    static ParamaterExecutor sSingleExecutor;
    static ParamaterExecutor sMainExecutor;

    public static ParamaterExecutor single() {
        if (sSingleExecutor == null) {
            synchronized (Executors.class) {
                if (sSingleExecutor == null) {
                    sSingleExecutor = new SingleExecutor();
                }
            }
        }
        return sSingleExecutor;
    }

    public static ParamaterExecutor main() {
        if (sMainExecutor == null) {
            synchronized (Executors.class) {
                if (sMainExecutor == null) {
                    sMainExecutor = new MainExecutor();
                }
            }
        }
        return sMainExecutor;
    }
}
