package com.naura.cityApp.di.module;

import com.naura.cityApp.basemodel.CityLoader;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CityLoaderModule {
    @Singleton
    @Provides
    public CityLoader getCityLoader() {
        return CityLoader.getInstance();
    }
}
