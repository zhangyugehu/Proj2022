package com.thssh.customview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

/**
 * @author hutianhang
 */
public class PaintActivity extends AppCompatActivity {

    PaintView painter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        painter = findViewById(R.id.painter);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

}