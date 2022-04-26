package androidx.navigation.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.SpecialFragmentNavigator;
import androidx.navigation.NavHostController;
import androidx.navigation.NavigatorProvider;

import com.thssh.commonlib.R;

public class HideNavHostFragment extends NavHostFragment {

    @Override
    protected void onCreateNavHostController(@NonNull NavHostController navHostController) {
        int id = getId() != 0 && getId() != View.NO_ID ? getId() : R.id.nav_host_fragment_container;

        NavigatorProvider navigatorProvider = navHostController.getNavigatorProvider();
        navigatorProvider.addNavigator(new SpecialFragmentNavigator(requireContext(), getParentFragmentManager(), id));
        super.onCreateNavHostController(navHostController);

//        int id = getId() != 0 && getId() != View.NO_ID ? getId() : R.id.nav_host_fragment_container;
//
//        NavigatorProvider navigatorProvider = navHostController.getNavigatorProvider();
//        navigatorProvider.addNavigator(new SpecialFragmentNavigator(requireContext(), getParentFragmentManager(), id));
//        navigatorProvider.addNavigator(new FragmentNavigator(requireContext(), getParentFragmentManager(), id));
//        navigatorProvider.addNavigator(new DialogFragmentNavigator(requireContext(), getParentFragmentManager()));
    }


}
