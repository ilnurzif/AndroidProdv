package com.naura.cityApp;

import android.app.Application;

import com.naura.cityApp.di.component.ApplicationComponent;
import com.naura.cityApp.di.component.DaggerApplicationComponent;
import com.naura.cityApp.di.module.ApplicationModule;
import com.naura.cityApp.di.module.CityLoaderModule;
import com.naura.cityApp.di.module.ConnectiveModule;
import com.naura.cityApp.di.module.DatabaseModule;
import com.naura.cityApp.di.module.ObservableModule;
import com.naura.cityApp.di.module.RetrofitModule;


public class App extends Application {
    private static App singleApp;
    protected static ApplicationComponent mApplicationComponent;

    public static App getInstance() {
        return singleApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleApp = this;

        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .connectiveModule(new ConnectiveModule(this))
                .retrofitModule(new RetrofitModule())
                .observableModule(new ObservableModule())
                .cityLoaderModule(new CityLoaderModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public static ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

}
