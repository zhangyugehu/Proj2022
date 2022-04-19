package com.tsh.navigation.state;

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

    public void listen(Listener<? extends State> listener) {
        listeners.add(listener);
    }

    public <T extends State> void setState(Callback<T> action) {
        action.invoke((T) this);
        for (Listener listener : listeners) {
            listener.onStateChanged((T)this);
        }
    }


}
