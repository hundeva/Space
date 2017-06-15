package com.hdeva.space.ui.home;

import android.support.v4.app.FragmentManager;

import com.hdeva.space.R;
import com.hdeva.space.base.ActivityPresenter;
import com.hdeva.space.service.notification.NotificationSchedulerService;

import javax.inject.Inject;

class HomeActivityPresenter extends ActivityPresenter<HomeActivity> {

    private HomeDrawerService homeDrawerService;
    private HomeBottomService homeBottomService;
    private HomeFragmentFactory homeFragmentFactory;
    private HomeApodService homeApodService;
    private HomeSearchService homeSearchService;

    @Inject
    public HomeActivityPresenter(HomeDrawerService homeDrawerService, HomeBottomService homeBottomService, HomeFragmentFactory homeFragmentFactory, HomeApodService homeApodService, HomeSearchService homeSearchService, NotificationSchedulerService notificationSchedulerService) {
        this.homeDrawerService = homeDrawerService;
        this.homeBottomService = homeBottomService;
        this.homeFragmentFactory = homeFragmentFactory;
        this.homeApodService = homeApodService;
        this.homeSearchService = homeSearchService;

        notificationSchedulerService.schedule();
    }

    @Override
    public void attach(HomeActivity activity) {
        super.attach(activity);
        homeDrawerService.setup(activity);
        homeBottomService.setup(activity);
        homeApodService.setup(activity);
        homeSearchService.setup(activity);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.home_frame) == null) {
            fragmentManager.beginTransaction().replace(R.id.home_frame, homeFragmentFactory.createDefaultHomeFragment()).commitNow();
        }
    }

    public boolean onBackPressed() {
        boolean handled;
        if (homeDrawerService.close()) {
            handled = true;
        } else if (homeSearchService.hideBottomSheet()) {
            handled = true;
        } else if (homeBottomService.showNavigation()) {
            handled = true;
        } else if (homeBottomService.swapToHome()) {
            handled = true;
        } else {
            handled = false;
        }
        return handled;
    }
}
