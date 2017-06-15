package com.hdeva.space.di;

import com.hdeva.space.core.net.module.NasaModule;
import com.hdeva.space.core.store.StoreModule;
import com.hdeva.space.service.notification.NotificationIntentService;
import com.hdeva.space.service.notification.NotificationModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface DaggerServiceInjectorModule {

    @ContributesAndroidInjector(modules = {
            StoreModule.class,
            NasaModule.class,
            NotificationModule.class,
    })
    NotificationIntentService contributeNotificationIntentService();
}
