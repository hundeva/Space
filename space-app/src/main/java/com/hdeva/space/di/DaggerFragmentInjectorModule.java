package com.hdeva.space.di;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.hdeva.space.core.net.module.NasaImagesModule;
import com.hdeva.space.core.net.module.NasaModule;
import com.hdeva.space.core.store.StoreModule;
import com.hdeva.space.ui.home.fragments.ApodFragment;
import com.hdeva.space.ui.home.fragments.PopularFragment;
import com.hdeva.space.ui.home.fragments.RecentFragment;
import com.hdeva.space.ui.viewer.content.ExoPlayerModule;
import com.hdeva.space.ui.viewer.content.ContentPlayerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface DaggerFragmentInjectorModule {

    @ContributesAndroidInjector
    SupportRequestManagerFragment contributeSupportRequestManagerFragment();

    @ContributesAndroidInjector(modules = {
            NasaImagesModule.class,
            StoreModule.class,
    })
    PopularFragment contributePopularFragment();

    @ContributesAndroidInjector(modules = {
            NasaImagesModule.class,
            StoreModule.class,
    })
    RecentFragment contributeRecentFragment();

    @ContributesAndroidInjector(modules = {
            NasaModule.class,
            StoreModule.class,
    })
    ApodFragment contributeApodFragment();

    @ContributesAndroidInjector(modules = {
            ExoPlayerModule.class,
    })
    ContentPlayerFragment contributeVideoPlayerContentFragment();
}
