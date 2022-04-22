package navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;

import navigation.fragment.NavigationFragment;

import com.thssh.commonlib.R;
import com.thssh.commonlib.logger.L;
import com.thssh.commonlib.utils.SafeObjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link androidx.navigation.fragment.FragmentNavigator} 使用replace切换Fragment
 * 虽然节省资源但会重新走生命周期
 * {@link HideFragmentNavigator} 使用hide show方式切换fragment
 */
@Navigator.Name("fragment")
public class HideFragmentNavigator extends Navigator<HideFragmentNavigator.Destination> {

    Context context;
    FragmentManager fragmentManager;
    int containerId;
    List<String> savedIds;

    public HideFragmentNavigator(Context context, FragmentManager fragmentManager, int containerId) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentPaused(fm, f);
                if (f instanceof NavigationFragment) {
                    ((NavigationFragment) f).willDisappear();
                }
            }

            @Override
            public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentStarted(fm, f);
                if (f instanceof NavigationFragment) {
                    ((NavigationFragment) f).willAppear();
                }
            }

            @Override
            public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDetached(fm, f);
                notifyPrevWillAppear();
            }
        }, true);
        savedIds = new ArrayList<>();
    }

    private void notifyPrevWillAppear(int... offsetArgs) {
        int offset = offsetArgs.length > 0 ? offsetArgs[0] : 0;
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.size() > 1) {
            Fragment fragment = fragments.get(fragments.size() - 1 - offset);
            if (fragment instanceof NavigationFragment) {
                ((NavigationFragment) fragment).willAppear();
            }
        }
    }

    @Override
    public void popBackStack(@NonNull NavBackStackEntry popUpTo, boolean savedState) {
        if (fragmentManager.isStateSaved()) {
            L.d("Ignoring popBackStack() call: FragmentManager has already saved its state");
            return;
        }
        if (savedState) {
            List<NavBackStackEntry> beforePopList = getState().getBackStack().getValue();
            NavBackStackEntry initialEntry = beforePopList.get(0);
            // Get the set of entries that are going to be popped
            List<NavBackStackEntry> poppedList = beforePopList.subList(
                    beforePopList.indexOf(popUpTo),
                    beforePopList.size()
            );
            // Now go through the list in reversed order (i.e., started from the most added)
            // and save the back stack state of each.
            for (int i = poppedList.size() - 1; i >= 0; i--) {
                NavBackStackEntry entry = poppedList.get(i);
                if (entry == initialEntry) {
                    L.d("FragmentManager cannot save the state of the initial destination $entry");
                } else {
                    fragmentManager.saveBackStack(entry.getId());
                    savedIds.add(entry.getId());
                }
            }
        } else {
            fragmentManager.popBackStack(popUpTo.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        getState().pop(popUpTo, savedState);
//        notifyPrevWillAppear(1);
    }

    @NonNull
    @Override
    public Destination createDestination() {
        return new Destination(this);
    }

    @Override
    public void navigate(@NonNull List<NavBackStackEntry> entries, @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
        if (fragmentManager.isStateSaved()) {
            L.d("Ignoring navigate() call: FragmentManager has already saved its state");
            return;
        }
        for (NavBackStackEntry entry : entries) {
            navigate(entry, navOptions, navigatorExtras);
        }
    }

    private void navigate(@NonNull NavBackStackEntry entry, @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
        List<NavBackStackEntry> backStack = getState().getBackStack().getValue();
        boolean initialNavigation = backStack.isEmpty();
        boolean restoreState = (navOptions != null && !initialNavigation && navOptions.shouldRestoreState() && savedIds.remove(entry.getId()));
        if (restoreState) {
            // Restore back stack does all the work to restore the entry
            fragmentManager.restoreBackStack(entry.getId());
            getState().push(entry);
            return;
        }
        Destination destination = (Destination) entry.getDestination();
        Bundle args = entry.getArguments();
        String className = destination.getClassName();
        if (className.startsWith(".")) {
            className = context.getPackageName() + className;
        }
        Fragment frag = fragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), className);
        frag.setArguments(args);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        int enterAnim = -1; // R.anim.slide_enter;
        int exitAnim =  -1; // R.anim.slide_exit;
        int popEnterAnim =  -1; // R.anim.slide_pop_enter;
        int popExitAnim =  -1; // R.anim.slide_pop_exit;
        if (navOptions != null) {
            enterAnim = SafeObjects.getOrDefault(navOptions.getEnterAnim(), enterAnim, -1);
            exitAnim = SafeObjects.getOrDefault(navOptions.getExitAnim(), exitAnim, -1);
            popEnterAnim = SafeObjects.getOrDefault(navOptions.getPopEnterAnim(), popEnterAnim, -1);
            popExitAnim = SafeObjects.getOrDefault(navOptions.getPopExitAnim(), popExitAnim, -1);
        }
        if (!SafeObjects.equalsAny(View.NO_ID, enterAnim, exitAnim, popEnterAnim, popExitAnim)) {
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        }

        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.size() <= 0) {
            ft.replace(containerId, frag);
        } else {
            Fragment prevFragment = fragments.get(fragments.size() - 1);
            ft.hide(prevFragment).add(containerId, frag);
            if (prevFragment instanceof NavigationFragment) {
                ((NavigationFragment) prevFragment).willDisappear();
            }
            if (frag.isAdded() && frag instanceof NavigationFragment) {
                // never
                ((NavigationFragment) frag).willAppear();
            }
        }
        @IdRes int destId = destination.getId();
        // TODO Build first class singleTop behavior for fragments
        boolean isSingleTopReplacement = (navOptions != null
                && !initialNavigation && navOptions.shouldLaunchSingleTop()
                && backStack.get(backStack.size() - 1).getDestination().getId() == destId);
        boolean isAdded;
        if (initialNavigation) {
            isAdded = true;
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (backStack.size() > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                fragmentManager.popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.addToBackStack(entry.getId());
            }
            isAdded = false;
        } else {
            ft.addToBackStack(entry.getId());
            isAdded = true;
        }
        if (navigatorExtras instanceof Extras) {
            for (Map.Entry<View, String> it : ((Extras)navigatorExtras).sharedElements.entrySet()) {
                ft.addSharedElement(it.getKey(), it.getValue());
            }
        }
        ft.setReorderingAllowed(true);
        ft.commit();
        // The commit succeeded, update our view of the world
        if (isAdded) {
            getState().push(entry);
        }
    }

    static class Extras implements Navigator.Extras {

        static class Builder {
            final Map<View, String> sharedElements = new LinkedHashMap<>();

            public Builder addSharedElements(Map<View, String> sharedElements) {
                this.sharedElements.putAll(sharedElements);
                return this;
            }

            public Builder addSharedElement(View sharedElement, String name) {
                sharedElements.put(sharedElement, name);
                return this;
            }

            public Extras build() {
                return new Extras(this);
            }

        }
        final Map<View, String> sharedElements;

        public Extras(Builder builder) {
            this.sharedElements = builder.sharedElements;
        }
    }

    static class Destination extends NavDestination {

        String className;

        public Destination(@NonNull Navigator<? extends Destination> fragmentNavigator) {
            super(fragmentNavigator);
        }

        @Override
        public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs) {
            super.onInflate(context, attrs);
            TypedArray typedArray = context.getResources().obtainAttributes(attrs, new int[] { android.R.attr.name });
            String className = typedArray.getString(0);
            if (className != null) {
                this.className = className;
            }
            typedArray.recycle();
        }

        public String getClassName() {
            return className;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Destination)) return false;
            return super.equals(o) && className.equals(((Destination) o).className);
        }

        @Override
        public int hashCode() {
            return 31 * super.hashCode() + className.hashCode();
        }
    }
}
