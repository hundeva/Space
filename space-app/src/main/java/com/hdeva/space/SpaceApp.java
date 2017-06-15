package com.hdeva.space;

import com.google.android.gms.ads.MobileAds;
import com.hdeva.space.di.DaggerApplicationComponent;
import com.jakewharton.threetenabp.AndroidThreeTen;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SpaceApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }

    private void init() {
        Paper.init(this);
        AndroidThreeTen.init(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        CalligraphyConfig.initDefault(createCalligraphyConfig());
    }

    private CalligraphyConfig createCalligraphyConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Comfortaa-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
