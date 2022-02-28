package com.thssh.activitygc;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.thssh.commonlib.logger.AbsLifeCycleActivity;

public class MemAllocActivity extends AbsLifeCycleActivity {

    TextView console;
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        h = new Handler();
        setContentView(R.layout.activity_mem_alloc);
        console = findViewById(R.id.console);
        loopMemInfo();
    }

    StringBuilder sb = new StringBuilder();
    private void loopMemInfo() {
        h.removeCallbacks(null);
        Runtime runtime = Runtime.getRuntime();
        long dalvikMax = runtime.maxMemory();
        long totalMem = runtime.totalMemory();
        long dalvikUsed = totalMem - runtime.freeMemory();

        sb.delete(0, sb.length());
        sb.append("Mem info\n");
        sb.append("dalvikMax: ");
        sb.append(Formatter.formatFileSize(this, dalvikMax) + "\n");
        sb.append("totalMem: ");
        sb.append(Formatter.formatShortFileSize(this, totalMem) + "\n");
        sb.append("dalvikUsed: ");
        sb.append(Formatter.formatFileSize(this, dalvikUsed) + "\n");
        console.setText(sb.toString());

        // loop call
        h.postDelayed(this::loopMemInfo, 1000);
    }

    int count = 0;
    byte[] bytes;
    public void allocMem(View view) {
        bytes = new byte[1024 * 1024 * 10 * count++];
        loopMemInfo();
    }
}