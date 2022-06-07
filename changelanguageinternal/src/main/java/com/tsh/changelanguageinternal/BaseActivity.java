package com.tsh.changelanguageinternal;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(AppContextWrapper.warp(newBase, Global.getAppLocale()));
    }
}
