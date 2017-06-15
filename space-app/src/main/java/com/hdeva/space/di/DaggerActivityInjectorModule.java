package com.hdeva.space.di;

import com.hdeva.space.core.net.module.NasaImagesModule;
import com.hdeva.space.core.net.module.NasaModule;
import com.hdeva.space.core.store.StoreModule;
import com.hdeva.space.service.notification.NotificationModule;
import com.hdeva.space.ui.home.HomeActivity;
import com.hdeva.space.ui.other.AboutActivity;
import com.hdeva.space.ui.other.SettingsActivity;
import com.hdeva.space.ui.search.SearchActivity;
import com.hdeva.space.ui.viewer.MediaViewerActivity;
import com.hdeva.space.ui.viewer.content.ContentHostActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface DaggerActivityInjectorModule {

    @ContributesAndroidInjector(modules = {
            NasaModule.class,
            NasaImagesModule.class,
            StoreModule.class,
            NotificationModule.class,
    })
    HomeActivity contributeHomeActivity();

    @ContributesAndroidInjector
    AboutActivity contributeAboutActivity();

    @ContributesAndroidInjector(modules = {
            StoreModule.class,
            NotificationModule.class,
    })
    SettingsActivity contributeSettingsActivity();

    @ContributesAndroidInjector(modules = {
            StoreModule.class,
    })
    MediaViewerActivity contributeMediaViewerActivity();

    @ContributesAndroidInjector(modules = {
            NasaImagesModule.class,
            StoreModule.class,
    })
    SearchActivity contributeSearchActivity();

    @ContributesAndroidInjector(modules = {
            StoreModule.class,
    })
    ContentHostActivity contributeContentHostActivity();
}
