package androidx.navigation.config;

public class NavConfig {
    public enum Type {
        Activity, Fragment, Graph, Dialog, Special
    }

    public static NavConfig newConfig() {
        NavConfig conf = new NavConfig();
        conf.type = Type.Special;
        return conf;
    }
    public boolean isStarter;
    public String className;
    public String deepLink;
    public int id;
    public Type type;

    public NavConfig setStarter(boolean isStarter) {
        this.isStarter = isStarter;
        return this;
    }

    public NavConfig setClassName(String className) {
        this.className = className;
        return this;
    }

    public NavConfig setDeepLink(String deepLink) {
        this.deepLink = deepLink;
        return this;
    }

    public NavConfig setId(int id) {
        this.id = id;
        return this;
    }

}
