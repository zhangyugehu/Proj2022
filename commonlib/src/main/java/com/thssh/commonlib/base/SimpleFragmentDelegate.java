package com.thssh.commonlib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.activity.DelegateFragment;
import com.thssh.commonlib.interfaces.IFragmentDelegate;

public class SimpleFragmentDelegate implements IFragmentDelegate {
    protected final DelegateFragment host;

    public SimpleFragmentDelegate(DelegateFragment host) {
        this.host = host;
    }

    @Override
    public void onAttach(@NonNull Context context) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }
}
