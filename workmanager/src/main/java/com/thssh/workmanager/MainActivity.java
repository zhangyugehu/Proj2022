package com.thssh.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        console = findViewById(R.id.console);

        LiveData<List<WorkInfo>> cleanWorkLiveData = WorkManager.getInstance(this).getWorkInfosByTagLiveData(TAG_CLEAN);
        cleanWorkLiveData.observe(this, list -> {
            console.setText("Workers: \n");
            for (int i = 0; i < list.size(); i++) {
                WorkInfo workInfo = list.get(i);
                console.append("INDEX: " + (i + 1));
                console.append("\n");
                console.append("\tstate: " + workInfo.getState());
                console.append("\n");
                console.append("\toutputData: " + workInfo.getOutputData());
                console.append("\n");
                console.append("\tprogress: " + workInfo.getProgress());
                console.append("\n\n");
            }
        });
    }

    private static final String TAG_CLEAN = "Work-Clean";
    private void doWork() {
        OneTimeWorkRequest clearWork = new OneTimeWorkRequest.Builder(CleanupWorker.class)
                .addTag(TAG_CLEAN)
                .setConstraints(new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                ).build();
        WorkManager.getInstance(this)
                .beginWith(clearWork)
                .enqueue();
    }

    public void startWork(View view) {
        doWork();
    }

    public void getWorkInfo(View view) {
        ListenableFuture<List<WorkInfo>> workInfoList = WorkManager.getInstance(this).getWorkInfosByTag(TAG_CLEAN);
        L.d("workInfoList: " + workInfoList);
    }
}