package com.thssh.commonlib.logger;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AbsLifeCycleFragment extends Fragment {
    protected String logTag() {
        return getClass().getSimpleName() + "@" + hashCode();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        L.d(logTag(), "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.d(logTag(), "onActivityCreated@" + getActivity().hashCode());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(logTag(), "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d(logTag(), "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d(logTag(), "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.d(logTag(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(logTag(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.d(logTag(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.d(logTag(), "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.d(logTag(), "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(logTag(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.d(logTag(), "onDetach");
    }
}
