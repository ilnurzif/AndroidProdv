package com.naura.cityApp.di.module;

import android.content.Context;

import com.naura.cityApp.basemodel.CityLoader;
import com.naura.cityApp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CityLoaderModule {
    @ApplicationContext private Context mContext;

    public CityLoaderModule(@ApplicationContext Context context) {
        this.mContext=context;
    }

    @Singleton
    @Provides
    public CityLoader getCityLoader() {
        return CityLoader.getInstance(mContext);
    }
}
