package com.hdeva.space.di;

import com.hdeva.space.core.store.StoreModule;
import com.hdeva.space.service.notification.NotificationBootReceiver;
import com.hdeva.space.service.notification.NotificationModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface DaggerBroadcastReceiverInjectorModule {

    @ContributesAndroidInjector(modules = {
            StoreModule.class,
            NotificationModule.class,
    })
    NotificationBootReceiver contributeNotificationBootReceiver();
}
