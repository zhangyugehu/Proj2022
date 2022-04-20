package com.tsh.navigation.state;

import android.content.Context;

import com.tsh.navigation.App;

import java.util.HashMap;
import java.util.Map;

public class Store {

    public enum Global {
        INSTANCE;

        public static Store getStore() {
            return INSTANCE.store();
        }

        private final Store store;
        Global() {
            store = createStore();
        }

        private Store createStore() {
            Store store = new Store();
            store.register(AuthState.NAME, new AuthState());
            return store;
        }

        Store store() {
            return store;
        }
    }

    public void install(Context context) {
        register(UserState.NAME, new UserState(context));
    }

    Map<String, State> store = new HashMap<>();

    public void register(String key, State state) {
        if (store.containsKey(key)) {
            throw new IllegalStateException("already register a state " + key);
        }
        store.put(key, state);
    }

    public <T extends State> T get(String key) {
        State state = store.get(key);
        if (state == null) {
            throw new IllegalStateException("register a state first");
        }
        return (T) state;
    }
}
