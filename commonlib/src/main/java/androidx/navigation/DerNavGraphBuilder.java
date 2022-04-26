package androidx.navigation;

import androidx.navigation.config.NavConfig;
import androidx.navigation.config.RouteMapping;
import androidx.navigation.fragment.DialogFragmentNavigator;
import androidx.navigation.fragment.FragmentNavigator;

import java.util.HashMap;
import java.util.Map;

import kotlin.NotImplementedError;

public class DerNavGraphBuilder {

    Map<String, NavConfig> navConfigs;

    public DerNavGraphBuilder() {
        navConfigs = new HashMap<>();
    }

    public DerNavGraphBuilder addConfig(String scheme, String className) {
        NavConfig conf = new NavConfig();
        conf.type = NavConfig.Type.Special;
        conf.className = className;
        return addConfig(scheme, conf);
    }

    public DerNavGraphBuilder addConfig(String scheme, NavConfig config) {
        navConfigs.put(scheme, config);
        return this;
    }

    public void build(NavController controller) {
        NavigatorProvider provider = controller.getNavigatorProvider();
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));
        FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        SpecialFragmentNavigator specialFragmentNavigator = provider.getNavigator(SpecialFragmentNavigator.class);
        NavGraphNavigator graphNavigator = provider.getNavigator(NavGraphNavigator.class);
        DialogFragmentNavigator dialogFragmentNavigator = provider.getNavigator(DialogFragmentNavigator.class);
        ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);
        for (Map.Entry<String, NavConfig> configEntry : navConfigs.entrySet()) {
            String scheme = configEntry.getKey();
            NavConfig config = configEntry.getValue();
            NavDestination destination = null;
            if (config.type == NavConfig.Type.Fragment) {
                destination = createFragmentDestination(fragmentNavigator, scheme, config);
            } else if (config.type == NavConfig.Type.Special) {
                destination = createSpecialDestination(specialFragmentNavigator, scheme, config);
            } else if (config.type == NavConfig.Type.Activity) {
                destination = createActivityDestination(activityNavigator, scheme, config);
            } else if (config.type == NavConfig.Type.Graph) {
                destination = createGraphDestination(graphNavigator, scheme, config);
            } else if (config.type == NavConfig.Type.Dialog) {
                destination = createDialogDestination(dialogFragmentNavigator, scheme, config);
            }
            if (destination != null) {
                navGraph.addDestination(destination);
                if (config.isStarter) {
                    navGraph.setStartDestination(destination.getId());
                }
            }
        }
        controller.setGraph(navGraph);
    }

    private NavDestination createDialogDestination(DialogFragmentNavigator navigator, String scheme, NavConfig config) {
        throw new NotImplementedError("implement Fragment first");
    }

    private NavDestination createGraphDestination(NavGraphNavigator navigator, String scheme, NavConfig config) {
        throw new NotImplementedError("implement Fragment first");
    }

    private NavDestination createActivityDestination(ActivityNavigator navigator, String scheme, NavConfig config) {
        throw new NotImplementedError("implement Fragment first");
    }

    private NavDestination createFragmentDestination(FragmentNavigator navigator, String scheme, NavConfig config) {
        throw new NotImplementedError("implement Fragment first");
    }

    private NavDestination createSpecialDestination(SpecialFragmentNavigator navigator, String scheme, NavConfig config) {
        SpecialFragmentNavigator.Destination destination = navigator.createDestination();
        destination.setClassName(config.className);
        destination.setRoute(scheme);
        if (config.id != 0){
            destination.setId(config.id);
        } else {
            destination.setId(RouteMapping.generateViewId(scheme));
        }
//        if (TextUtils.isEmpty(config.deepLink)) {
//            for (String path : paths) {
//                destination.addDeepLink(String.format("%s/%s", path, scheme));
//            }
//        } else {
//            destination.addDeepLink(config.deepLink);
//        }
        destination.addDeepLink(scheme);
        return destination;
    }
}
