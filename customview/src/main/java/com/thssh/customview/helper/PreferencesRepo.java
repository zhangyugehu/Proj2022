package com.thssh.customview.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author hutianhang
 */
public class PreferencesRepo implements IRepo<PathInfo> {
    private static final String NAME = "path_info";
    private static final String KEY_PATH = "_path";

    private final SharedPreferences preferences;

    public PreferencesRepo(Context context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void save(PathInfo data) {
        preferences.edit().putString(KEY_PATH, data.stringify()).apply();
    }

    @Override
    public PathInfo restore() {
        return new PathInfo(preferences.getString(KEY_PATH, ""));
    }
}
