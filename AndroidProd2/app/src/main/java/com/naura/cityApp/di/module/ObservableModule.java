package com.naura.cityApp.di.module;

import com.naura.cityApp.observercode.Observable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ObservableModule {
    @Singleton
    @Provides
    public Observable getObservable() {
      //  return new Observable();
        return Observable.getInstance();
    }
}
