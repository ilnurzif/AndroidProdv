package com.naura.cityApp.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.naura.cityApp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class SharedPrefPropModule {

    @Provides
    @Singleton
    public SharedPreferences getSharedPref(@ApplicationContext Context context) {
        return context.getSharedPreferences("citySetting", MODE_PRIVATE);
    }

    @Provides
    public SharedPreferences.Editor getSharetPrefEditor(SharedPreferences sharedPref) {
        return sharedPref.edit();
    }

}
