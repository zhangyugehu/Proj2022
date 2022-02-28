package com.thssh.commonlib.interfaces;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IActivityDelegate {
    
    void onCreate(@Nullable Bundle savedInstanceState);

    
    void onStart();

    
    void onResume();

    
    void onPause();

    
    void onRestart();

    
    void onStop();

    
    void onDestroy();

    
    void attachBaseContext(Context newBase);

    
    void onPostCreate(@Nullable Bundle savedInstanceState);

    
    void onPostResume();

    
    void onRestoreInstanceState(@NonNull Bundle savedInstanceState);

    
    void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState);

    
    void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState);

    
    void onTopResumedActivityChanged(boolean isTopResumedActivity);

    
    void onAttachedToWindow();

    
    void onDetachedFromWindow();
}
