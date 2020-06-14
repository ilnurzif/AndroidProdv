package com.naura.cityApp.di.module;

import android.content.Context;

import com.naura.cityApp.di.ApplicationContext;
import com.naura.cityApp.location.CityLocation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CityLocationModule {
    @Provides
    @Singleton
    public CityLocation getCityLocation(@ApplicationContext Context context) {
        return CityLocation.getInstance(context);
    }

}


