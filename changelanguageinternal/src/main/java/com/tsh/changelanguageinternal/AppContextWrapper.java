package com.tsh.changelanguageinternal;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

public class AppContextWrapper extends ContextWrapper {

    public static ContextWrapper warp(Context context, Locale locale) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            sysLocale = config.locale;
        }
        if (sysLocale != locale) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale);
                config.setLocales(new LocaleList(locale));
//                context.createConfigurationContext(config);
            } else {
                config.locale = locale;
            }
            Locale.setDefault(locale);
//            context = context.createConfigurationContext(config);
        }
        return new AppContextWrapper(context);
    }

    public AppContextWrapper(Context base) {
        super(base);
    }
}
