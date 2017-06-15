package com.hdeva.space.ui.home;

import android.support.v4.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.hdeva.space.R;
import com.hdeva.space.ui.helper.BottomBarHelper;
import com.hdeva.space.ui.home.fragments.ApodFragment;
import com.hdeva.space.ui.home.fragments.PopularFragment;
import com.hdeva.space.ui.home.fragments.RecentFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

class HomeFragmentFactory {

    private static final Map<String, Fragment> FRAGMENTS = new HashMap<>();

    private BottomBarHelper bottomBarHelper;

    @Inject
    public HomeFragmentFactory(BottomBarHelper bottomBarHelper) {
        this.bottomBarHelper = bottomBarHelper;
    }

    public Fragment createFragment(String tag) {
        Fragment fragment = FRAGMENTS.get(tag);
        if (fragment == null) {
            switch (tag) {
                case PopularFragment.TAG:
                    fragment = new PopularFragment();
                    break;
                case RecentFragment.TAG:
                    fragment = new RecentFragment();
                    break;
                case ApodFragment.TAG:
                    fragment = new ApodFragment();
                    break;
                default:
                    throw new IllegalStateException("Invalid fragment requested for tag: " + tag);
            }

            FRAGMENTS.put(tag, fragment);
        }
        return fragment;
    }

    public String positionToTag(int position) {
        switch (position) {
            case 0:
                return PopularFragment.TAG;
            case 1:
                return RecentFragment.TAG;
            case 2:
                return ApodFragment.TAG;
            default:
                throw new IllegalStateException("Invalid fragment requested: " + position);
        }
    }

    public Fragment createDefaultHomeFragment() {
        return createFragment(PopularFragment.TAG);
    }

    public void setupBottomNavigation(AHBottomNavigation bottomNavigation) {
        bottomBarHelper.addMenuItem(bottomNavigation, R.string.popular, R.drawable.ic_fire_black_24dp);
        bottomBarHelper.addMenuItem(bottomNavigation, R.string.recent, R.drawable.ic_earth_black_24dp);
        bottomBarHelper.addMenuItem(bottomNavigation, R.string.apod, R.drawable.ic_apod_black_24dp);
    }
}
