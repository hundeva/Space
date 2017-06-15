package com.hdeva.space.di;

import android.content.Context;

import com.hdeva.space.SpaceApp;

import dagger.Binds;
import dagger.Module;

@Module
interface DaggerApplicationInjectorModule {

    @Binds
    Context application(SpaceApp app);

}
