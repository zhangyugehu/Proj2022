package com.tsh.simpleutil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt = findViewById(R.id.txt);
        File cacheDir = getCacheDir();
        String cacheDirAbsolutePath = cacheDir.getAbsolutePath();
        txt.setText("");
        txt.append("Env:\n");
        txt.append("cacheDirAbsolutePath: ");
        txt.append(cacheDirAbsolutePath);
    }
}