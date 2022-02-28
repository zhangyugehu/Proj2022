package com.thssh.commonlib.interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IFragmentDelegate {

    void onAttach(@NonNull Context context);

    
    void onActivityCreated(@Nullable Bundle savedInstanceState);

    
    void onCreate(@Nullable Bundle savedInstanceState);

    @Nullable
    View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    
    void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState);

    
    void onStart();

    
    void onResume();

    
    void onPause();

    
    void onStop();

    
    void onDestroyView();

    
    void onDestroy();

    
    void onDetach();
}
