package com.tsh.navigation.repos.util;

import com.tsh.navigation.state.State;

public interface RepoCallback<T> {
    void call(T data);
}
