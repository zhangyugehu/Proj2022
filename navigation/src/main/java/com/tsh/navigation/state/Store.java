package com.tsh.navigation.state;

import java.util.HashMap;
import java.util.Map;

public class Store {
    Map<String, State> store = new HashMap<>();

    public void addState(String key, State state) {
        store.put(key, state);
    }

    public <T extends State> T get(String key) {
        return (T) store.get(key);
    }
}
