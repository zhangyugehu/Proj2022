package com.thssh.commonlib.logger;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.interfaces.IFragmentDelegate;

public class FragmentLifeCycleDelegate implements IFragmentDelegate {

    private Activity activity;
    private final String tag;

    public FragmentLifeCycleDelegate(String tag) {
        this.tag = tag;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String logTag() {
        return tag;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        L.d(logTag(), "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        L.d(logTag(), "onActivityCreated@" + (activity != null ? activity.hashCode() : "null"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        L.d(logTag(), "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d(logTag(), "onCreateView");
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        L.d(logTag(), "onViewCreated");
    }

    @Override
    public void onStart() {
        L.d(logTag(), "onStart");
    }

    @Override
    public void onResume() {
        L.d(logTag(), "onResume");
    }

    @Override
    public void onPause() {
        L.d(logTag(), "onPause");
    }

    @Override
    public void onStop() {
        L.d(logTag(), "onStop");
    }

    @Override
    public void onDestroyView() {
        L.d(logTag(), "onDestroyView");
    }

    @Override
    public void onDestroy() {
        L.d(logTag(), "onDestroy");
    }

    @Override
    public void onDetach() {
        L.d(logTag(), "onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        L.d(logTag(), "onHiddenChanged", hidden);
    }
}
