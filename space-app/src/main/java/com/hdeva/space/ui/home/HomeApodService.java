package com.hdeva.space.ui.home;

import android.support.design.widget.AppBarLayout;
import android.view.View;

import com.hdeva.space.R;
import com.hdeva.space.core.model.apod.Apod;
import com.hdeva.space.core.repository.ApodRepository;
import com.hdeva.space.core.service.DateService;
import com.hdeva.space.core.store.ApodStore;
import com.hdeva.space.core.helper.GlideHelper;
import com.hdeva.space.ui.navigator.MediaViewerNavigator;

import javax.inject.Inject;

class HomeApodService {

    private ApodStore apodStore;
    private ApodRepository apodRepository;
    private GlideHelper glideHelper;
    private DateService dateService;
    private MediaViewerNavigator navigator;

    @Inject
    public HomeApodService(ApodStore apodStore, ApodRepository apodRepository, GlideHelper glideHelper, DateService dateService, MediaViewerNavigator navigator) {
        this.apodStore = apodStore;
        this.apodRepository = apodRepository;
        this.glideHelper = glideHelper;
        this.dateService = dateService;
        this.navigator = navigator;
    }

    public void setup(HomeActivity activity) {
        activity.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> onAppBarOffsetChanged(activity, appBarLayout, verticalOffset));
        showApod(activity, apodStore.getLastKnownApod());
        if (!apodStore.hasApodForToday()) {
            apodRepository.getApodForToday().subscribe(apod -> showApod(activity, apod), (t) -> showApod(activity, apodStore.getLastKnownApod()));
        }
    }

    private void showApod(HomeActivity activity, Apod apod) {
        if (apod == null) {
            activity.parallax.setImageResource(R.drawable.earth);
            activity.parallaxTitle.setText(R.string.earth);
            activity.parallaxSubTitle.setVisibility(View.GONE);
        } else {
            glideHelper.setApodImageAsync(activity.parallax, apod, () -> {
                activity.parallaxTitle.setText(apod.title);
                activity.parallaxSubTitle.setText(activity.getString(R.string.apod_date, dateService.parseApodDateFormat(apod.date)));
                activity.parallaxSubTitle.setVisibility(View.VISIBLE);
                activity.parallax.setOnClickListener(v -> navigator.openApod(activity, apod, activity.parallax));
            });
        }
    }

    private void onAppBarOffsetChanged(HomeActivity activity, AppBarLayout appBarLayout, int verticalOffset) {
        float offset = 1 - (float) (appBarLayout.getTotalScrollRange() + verticalOffset) / appBarLayout.getTotalScrollRange();
        activity.title.setAlpha(offset);
    }
}
