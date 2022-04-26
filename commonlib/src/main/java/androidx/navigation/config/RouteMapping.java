package androidx.navigation.config;

import android.view.View;

import com.thssh.commonlib.utils.SafeObjects;

import java.util.HashMap;
import java.util.Map;

public enum RouteMapping {
    INSTANCE;
    private final Map<String, Integer> mapping;

    RouteMapping() {
        mapping = new HashMap<>();
    }

    public static int findIdByRoute(String route) {
        return SafeObjects.getOrDefault(RouteMapping.INSTANCE.mapping.get(route), View.NO_ID);
    }

    public static Integer generateViewId(String route) {
        int id;
        RouteMapping.INSTANCE.mapping.put(route, (id = View.generateViewId()));
        return id;
    }
}
