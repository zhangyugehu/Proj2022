package com.tsh.changelanguageinternal;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.Locale;

public enum Global {
    @SuppressLint("StaticFieldLeak") INSTANCE;
    private Locale locale;
    private Context context;

    Global() {
        locale = Locale.TRADITIONAL_CHINESE;
    }

    public static void setAppLocale(Locale locale) {
        INSTANCE.locale = locale;
    }

    public static Locale getAppLocale() {
        return INSTANCE.locale;
    }

    public static void setApplicationContext(Context context) {
        INSTANCE.context = context;
    }

    public static Context getContext() {
        return INSTANCE.context;
    }

    public static Context requireContext() {
        if (null == INSTANCE.context) {
            throw new IllegalStateException("context does not set yet.");
        }
        return INSTANCE.context;
    }
}
