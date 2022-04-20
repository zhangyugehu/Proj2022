package com.tsh.navigation.state;

import android.content.Context;

import com.tsh.navigation.repos.UserRepo;

public class UserState extends State {
    public static final String NAME = "_state_user";
    private final transient UserRepo.Impl repo;

    public String username;
    public String password;
    public String token;

    public UserState(Context context) {
        this.repo = new UserRepo.StorageRepo(context);
        repo.restore(this::sync);
    }

    private void sync(UserState state) {
        if (state != null) {
            username = state.username;
            password = state.password;
            token = state.token;
        }
    }

    @Override
    protected <T extends State> void notifyStateChanged(T state) {
        super.notifyStateChanged(state);
        if (repo != null) {
            repo.save(this);
        }
    }

    @Override
    public String toString() {
        return "UserState{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    String stringify() {
        return username + "," + password + "," + token;
    }
}
