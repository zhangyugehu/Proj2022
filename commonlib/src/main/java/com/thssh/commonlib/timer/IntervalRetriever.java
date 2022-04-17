package com.thssh.commonlib.timer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hutianhang
 */

public enum IntervalRetriever implements Handler.Callback {
    /**
     * Singleton
     */
    INSTANCE;

    private static final String FRAG_TAG = "Interval_Fragment_Tag";

    Map<FragmentManager, IntervalFragment> pendingIntervalFragments;
    Handler handler;

    IntervalRetriever() {
        pendingIntervalFragments = new HashMap<>();
        handler = new Handler(Looper.getMainLooper(), this);
    }

    static IntervalRetriever get() {
        return INSTANCE;
    }


    public IntervalFragment get(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        return fragmentGet(fm);
    }

    public Lifecycle get(Fragment fragment) {
        return get(fragment.requireActivity());
    }

    private IntervalFragment fragmentGet(FragmentManager fm) {
        return getIntervalFragment(fm);

    }

    private IntervalFragment getIntervalFragment(FragmentManager fm) {
        IntervalFragment fragment = (IntervalFragment) fm.findFragmentByTag(FRAG_TAG);
        if (fragment == null) {
            fragment = pendingIntervalFragments.get(fm);
        }
        if (fragment == null) {
            fragment = new IntervalFragment();
            pendingIntervalFragments.put(fm, fragment);
            fm.beginTransaction().add(fragment, FRAG_TAG).commitAllowingStateLoss();
            handler.obtainMessage(1, fm).sendToTarget();
        }
        return fragment;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == 1) {
            FragmentManager fm = (FragmentManager) msg.obj;
            pendingIntervalFragments.remove(fm);
            return true;
        }
        return false;
    }
}
