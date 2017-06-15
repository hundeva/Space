package com.hdeva.space.di;

import com.hdeva.space.SpaceApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        DaggerApplicationInjectorModule.class,
        DaggerActivityInjectorModule.class,
        DaggerFragmentInjectorModule.class,
        DaggerServiceInjectorModule.class,
        DaggerBroadcastReceiverInjectorModule.class,
})
interface ApplicationComponent extends AndroidInjector<SpaceApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SpaceApp> {

    }
}
