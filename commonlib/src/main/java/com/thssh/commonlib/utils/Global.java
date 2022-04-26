package com.thssh.commonlib.utils;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Global {
    static Application sApp;
    public static Application getApp() {
        if (sApp == null) {
            try {
                Method currentApplicationMethod = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                Object invokedObj = currentApplicationMethod.invoke(null);
                if (invokedObj instanceof Application) {
                    sApp = (Application) invokedObj;
                }
            } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException ignored) {
            }
        }
        return sApp;
    }
}
