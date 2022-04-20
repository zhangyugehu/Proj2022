package com.tsh.navigation.state;

import android.text.TextUtils;

import com.thssh.commonlib.logger.L;

import java.util.HashSet;
import java.util.Set;

public abstract class State {
    public interface Listener<T extends State> {
        void onStateChanged(T state);
    }

    public interface Callback<T extends State> {
        void invoke(T state);
    }

    final Set<Listener<? extends State>> listeners = new HashSet<>();

    public <T extends State> void listen(Listener<T> listener) {
        listeners.add(listener);
    }

    public <T extends State> void setState(Callback<T> action) {
        String oldStringify = stringify();
        action.invoke((T) this);
        String newStringify = stringify();
        if (TextUtils.equals(oldStringify, newStringify)) {
            L.d("setState", "state not changed.");
        } else {
            notifyStateChanged((T)this);
        }
    }

    protected <T extends State> void notifyStateChanged(T state) {
        for (Listener listener : listeners) {
            listener.onStateChanged(state);
        }
    }

    /**
     * help diff changed, reduce {@link Listener#onStateChanged(State)}
     * @return
     */
    abstract String stringify();

}
