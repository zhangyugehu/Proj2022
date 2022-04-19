package com.thssh.customview.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.thssh.customview.views.CircleImageView;
import com.thssh.customview.R;

public class MainActivity extends AppCompatActivity {

    private CircleImageView civ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        civ = findViewById(R.id.civ_main);
//        civ.setDrawable(ContextCompat.getDrawable(this, R.drawable.test));
//        civ.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.test));


    }
}