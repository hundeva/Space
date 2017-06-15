package com.hdeva.space.ui.home;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.hdeva.space.R;
import com.hdeva.space.service.ContentShareHelper;
import com.hdeva.space.ui.other.AboutActivity;
import com.hdeva.space.ui.other.SettingsActivity;

import javax.inject.Inject;

class HomeDrawerService {

    private ContentShareHelper contentShareHelper;

    private HomeActivity activity;

    @Inject
    public HomeDrawerService(ContentShareHelper contentShareHelper) {
        this.contentShareHelper = contentShareHelper;
    }

    public void setup(final HomeActivity activity) {
        this.activity = activity;
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, activity.drawerLayout, activity.getSupportToolbar(), R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(false);
        activity.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        activity.navigationView.setNavigationItemSelectedListener(this::selectNavigationItem);
    }

    private boolean selectNavigationItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_settings:
                SettingsActivity.launch(activity);
                break;
            case R.id.drawer_about:
                AboutActivity.launch(activity);
                break;
            case R.id.drawer_share:
                contentShareHelper.shareApp();
                break;
        }
        activity.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean close() {
        boolean handled;
        if (activity.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activity.drawerLayout.closeDrawer(GravityCompat.START);
            handled = true;
        } else {
            handled = false;
        }
        return handled;
    }
}
