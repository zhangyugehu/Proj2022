package com.thssh.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class CleanupWorker extends Worker {
    public CleanupWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (new Random().nextInt() % 2 == 0) {
            L.d("doWork: retry.");
            return Result.retry();
        } else {
            L.d("doWork: success.");
            return Result.success();
        }
    }
}
