package com.thssh.commonlib.timer;

import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hutianhang
 */
public class IntervalFragment extends Fragment implements Lifecycle {

    Set<LifecycleListener> lifecycleListeners;

    public IntervalFragment() {
        lifecycleListeners = new HashSet<>();
    }

    @Override
    public void addListener(LifecycleListener listener) {
        lifecycleListeners.add(listener);
    }

    @Override
    public void onStart() {
        super.onStart();
        for (LifecycleListener listener : lifecycleListeners) {
            listener.onStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (LifecycleListener listener : lifecycleListeners) {
            listener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (LifecycleListener listener : lifecycleListeners) {
            listener.onStop();
        }
    }
}
