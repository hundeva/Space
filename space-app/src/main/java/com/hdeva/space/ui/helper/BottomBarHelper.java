package com.hdeva.space.ui.helper;

import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.hdeva.space.R;

import javax.inject.Inject;

public class BottomBarHelper {

    @Inject
    public BottomBarHelper() {
    }

    public void setup(AHBottomNavigation bottomNavigation) {
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(bottomNavigation.getContext(), R.color.bottom_navigation_background));
        bottomNavigation.setAccentColor(ContextCompat.getColor(bottomNavigation.getContext(), R.color.bottom_navigation_selected));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(bottomNavigation.getContext(), R.color.bottom_navigation_unselected));
    }

    public void addMenuItem(AHBottomNavigation bottomNavigation, int labelResId, int iconResId) {
        bottomNavigation.addItem(new AHBottomNavigationItem(labelResId, iconResId, R.color.bottom_navigation_background));
    }

    public boolean swapToHome(AHBottomNavigation bottomNavigation) {
        boolean handled;
        if (bottomNavigation.getCurrentItem() != 0) {
            bottomNavigation.setCurrentItem(0);
            handled = true;
        } else {
            handled = false;
        }
        return handled;
    }
}
