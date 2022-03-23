package com.thssh.commonlib.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thssh.commonlib.interfaces.IFragmentDelegate;
import com.thssh.commonlib.logger.FragmentLifeCycleDelegate;

import java.util.ArrayList;
import java.util.List;

public class DelegateFragment extends Fragment {
    private final List<IFragmentDelegate> mFragmentDelegate;

    protected FragmentLifeCycleDelegate lifeCycleDelegate;

    public DelegateFragment() {
        mFragmentDelegate = new ArrayList<>();
        lifeCycleDelegate = new FragmentLifeCycleDelegate(getClass().getSimpleName() + "@" + hashCode());
        registerFragmentDelegate(lifeCycleDelegate);
    }

    public void registerFragmentDelegate(IFragmentDelegate delegate) {
        if (!mFragmentDelegate.contains(delegate)) {
            mFragmentDelegate.add(delegate);
        }
    }

    public void unregisterFragmentDelegate(IFragmentDelegate delegate) {
        mFragmentDelegate.remove(delegate);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onAttach(context);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifeCycleDelegate.setActivity(getActivity());
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onCreate(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onCreateView(inflater, container, savedInstanceState);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onViewCreated(view, savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        for (IFragmentDelegate delegate : mFragmentDelegate) {
            delegate.onDetach();
        }
    }
}
