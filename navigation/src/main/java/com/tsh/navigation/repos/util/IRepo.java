package com.tsh.navigation.repos.util;

import com.tsh.navigation.state.State;

public interface IRepo<T extends State> {
    void save(T state);

    void restore(RepoCallback<T> callback);
}
