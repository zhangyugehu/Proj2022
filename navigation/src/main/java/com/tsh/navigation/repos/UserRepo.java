package com.tsh.navigation.repos;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tsh.navigation.repos.util.IRepo;
import com.tsh.navigation.repos.util.RepoCallback;
import com.tsh.navigation.state.UserState;

public class UserRepo {

    public static abstract class Impl implements IRepo<UserState> {

    }

    public static class StorageRepo extends Impl {

        private static final String NAME = "_pref_user";
        private static final String KEY_STRINGIFY = "_key_stringify";

        private final SharedPreferences preferences;
        private final Gson gson;

        public StorageRepo(Context context) {
            if (context == null) throw new IllegalArgumentException("context cant null");
            gson = new Gson();
            preferences = context.getApplicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }

        @Override
        public void save(UserState state) {
            preferences.edit().putString(KEY_STRINGIFY, storeState(state)).apply();
        }

        @Override
        public void restore(RepoCallback<UserState> callback) {
            callback.call(restoreStore(preferences.getString(KEY_STRINGIFY, null)));
        }

        private UserState restoreStore(String stringify) {
            return gson.fromJson(stringify, UserState.class);
        }

        private String storeState(UserState state) {
            return gson.toJson(state);
        }
    }

}
