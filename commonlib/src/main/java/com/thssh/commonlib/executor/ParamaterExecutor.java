package com.thssh.commonlib.executor;

import com.thssh.commonlib.interfaces.Callback1;

import java.util.concurrent.Executor;

public abstract class ParamaterExecutor implements Executor, Runnable {

    Object param;
    Callback1<Object> command;
    public <T> void execute(T param, Callback1<T> command) {
        this.param = param;
        this.command = (Callback1<Object>) command;
        execute(this);
    }

    @Override
    public void run() {
        command.invoke(param);
    }
}
