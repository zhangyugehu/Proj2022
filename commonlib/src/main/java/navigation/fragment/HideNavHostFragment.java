package navigation.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavHostController;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.DialogFragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import com.thssh.commonlib.R;

import navigation.HideFragmentNavigator;

public class HideNavHostFragment extends NavHostFragment {

    @Override
    protected void onCreateNavHostController(@NonNull NavHostController navHostController) {
        /**
         * Done this on purpose.
         */
        if (false) {
            super.onCreateNavHostController(navHostController);
        }

        int id = getId() != 0 && getId() != View.NO_ID ? getId() : R.id.nav_host_fragment_container;

        NavigatorProvider navigatorProvider = navHostController.getNavigatorProvider();
        navigatorProvider.addNavigator(new HideFragmentNavigator(requireContext(), getParentFragmentManager(), id));
        navigatorProvider.addNavigator(new DialogFragmentNavigator(requireContext(), getParentFragmentManager()));
    }

}
