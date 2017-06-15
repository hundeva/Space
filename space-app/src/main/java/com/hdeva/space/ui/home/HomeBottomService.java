package com.hdeva.space.ui.home;

import android.support.v4.app.Fragment;

import com.hdeva.space.R;
import com.hdeva.space.ui.helper.BottomBarHelper;
import com.hdeva.space.ui.home.fragments.HomeFragment;

import javax.inject.Inject;

class HomeBottomService {

    private HomeFragmentFactory homeFragmentFactory;
    private BottomBarHelper bottomBarHelper;
    private HomeActivity activity;

    @Inject
    public HomeBottomService(HomeFragmentFactory homeFragmentFactory, BottomBarHelper bottomBarHelper) {
        this.homeFragmentFactory = homeFragmentFactory;
        this.bottomBarHelper = bottomBarHelper;
    }

    public void setup(HomeActivity activity) {
        this.activity = activity;
        bottomBarHelper.setup(activity.bottomNavigation);
        activity.bottomNavigation.setOnTabSelectedListener(this::swapFragment);
        activity.bottomNavigation.manageFloatingActionButtonBehavior(activity.fab);
        homeFragmentFactory.setupBottomNavigation(activity.bottomNavigation);
    }

    private boolean swapFragment(int position, boolean wasSelected) {
        if (wasSelected) {
            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.home_frame);
            if (fragment != null && fragment instanceof HomeFragment) {
                ((HomeFragment) fragment).onFragmentReselected();
            }
        } else {
            String tag = homeFragmentFactory.positionToTag(position);
            Fragment fragment = homeFragmentFactory.createFragment(tag);
            replace(fragment);
        }
        return true;
    }

    private void replace(Fragment fragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.home_frame, fragment)
                .commitNowAllowingStateLoss();
    }

    public boolean swapToHome() {
        return bottomBarHelper.swapToHome(activity.bottomNavigation);
    }

    public boolean showNavigation() {
        boolean handled;
        if (activity.bottomNavigation.isHidden()) {
            activity.bottomNavigation.restoreBottomNavigation();
            handled = true;
        } else {
            handled = false;
        }
        return handled;
    }
}
