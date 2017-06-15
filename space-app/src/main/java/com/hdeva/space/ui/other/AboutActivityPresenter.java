package com.hdeva.space.ui.other;

import com.hdeva.space.R;
import com.hdeva.space.base.ActivityPresenter;
import com.hdeva.space.core.service.AppInfoProvider;

import javax.inject.Inject;

class AboutActivityPresenter extends ActivityPresenter<AboutActivity> {

    private AppInfoProvider appInfoProvider;

    @Inject
    public AboutActivityPresenter(AppInfoProvider appInfoProvider) {
        this.appInfoProvider = appInfoProvider;
    }

    @Override
    public void attach(AboutActivity activity) {
        super.attach(activity);
        AppInfoProvider.AppInfo appInfo = appInfoProvider.getAppInfo();
        activity.appVersion.setText(activity.getString(R.string.app_version, appInfo.versionName, appInfo.versionCode));
    }
}
