package com.thssh.launchmode;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thssh.commonlib.logger.L;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Boolean> launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(new ActivityResultContract<Boolean, String>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Boolean input) {
                L.td("createIntent", input);
                return new Intent(MainActivity.this, StandardActivity.class);
            }

            @Override
            public String parseResult(int resultCode, @Nullable Intent intent) {
                L.td("parseResult", "resultCode:", resultCode, "intent:", intent);
                return "result-hahaha";
            }
        }, new ActivityResultCallback<String>() {
            @Override
            public void onActivityResult(String result) {
                L.td("onActivityResult", "result:", result);
            }
        });
    }

    public void startStandardActivity(View view) {
//        startActivity(new Intent(this, StandardActivity.class));
        launcher.launch(true);
    }

    public void startSingleTopActivity(View view) {
        startActivity(new Intent(this, SingleTopActivity.class));
    }

    public void startSingleTaskActivity(View view) {
        startActivity(new Intent(this, SingleTaskActivity.class));
    }

    public void startSingleInstanceActivity(View view) {
        startActivity(new Intent(this, SingleInstanceActivity.class));
    }

    public void startSingleInstancePreTaskActivity(View view) {
        startActivity(new Intent(this, SingleInstancePerTaskActivity.class));
    }

    public void startNextActivity(View view) {
        TestFragment testFragment = new TestFragment();
        Intent intent = new Intent();
        intent.putExtra("form", "startActivityFromFragment");
        startActivityFromFragment(testFragment, intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d("requestCode:", requestCode, "resultCode:", requestCode, "data:", data);
    }
}