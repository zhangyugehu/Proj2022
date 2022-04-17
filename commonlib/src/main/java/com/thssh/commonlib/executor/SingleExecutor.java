package com.thssh.commonlib.executor;

import com.thssh.commonlib.logger.L;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hutianhang
 */
public class SingleExecutor extends ParamaterExecutor {

    private final ThreadPoolExecutor threadPoolExecutor;

    public SingleExecutor() {
        threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                L.td("Runnable DROP!!!", r.hashCode());
            }
        });
    }

    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
