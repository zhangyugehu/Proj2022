package com.thssh.commonlib.timer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hutianhang
 */

public enum IntervalRetriever {
    /**
     * Singleton
     */
    INSTANCE;

    private static final String FRAG_TAG = "Interval_Fragment_Tag";

    Map<FragmentManager, IntervalFragment> pendingIntervalFragments;

    IntervalRetriever() {
        pendingIntervalFragments = new ConcurrentHashMap<>();
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
        }
        return fragment;
    }
}
