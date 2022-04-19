package com.thssh.commonlib.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class BaseFragment extends DelegateFragment {

    protected View mRootView;

    protected ActionBar requireActionBar() {
        FragmentActivity activity = requireActivity();
        if (activity instanceof AppCompatActivity) {
            return ((AppCompatActivity) activity).getSupportActionBar();
        }
        return null;
    }

    protected void setTitle(CharSequence title) {
        ActionBar actionBar = requireActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(provideLayoutId(), container, false);
    }

    public int provideLayoutId() {
        return View.NO_ID;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (mRootView == null) {
            throw new IllegalStateException("call onCreateView provide a root view. then call after onViewCreated.");
        }
        return mRootView.findViewById(id);
    }
}
