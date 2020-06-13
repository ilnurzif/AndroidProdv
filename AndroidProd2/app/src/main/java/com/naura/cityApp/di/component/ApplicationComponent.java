package com.naura.cityApp.di.component;

import android.content.Context;

import com.naura.cityApp.App;
import com.naura.cityApp.mainactivity.MainActivity;
import com.naura.cityApp.basemodel.CityLoader;
import com.naura.cityApp.database.basecode.CityDao;
import com.naura.cityApp.di.ApplicationContext;
import com.naura.cityApp.di.module.ApplicationModule;
import com.naura.cityApp.di.module.CityLoaderModule;
import com.naura.cityApp.di.module.ConnectiveModule;
import com.naura.cityApp.di.module.DatabaseModule;
import com.naura.cityApp.di.module.ObservableModule;
import com.naura.cityApp.di.module.RetrofitModule;
import com.naura.cityApp.fragments.citydetail.CityDetailPresenter;
import com.naura.cityApp.fragments.citylist.CityListPresenter;
import com.naura.cityApp.fragments.searchhistory.SearchCityPresenter;
import com.naura.cityApp.mainactivity.MainActivityPresener;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class,
        ConnectiveModule.class,
        RetrofitModule.class,
        ObservableModule.class,
        CityLoaderModule.class
})
public interface ApplicationComponent {
    void inject(App application);
    void inject(CityLoader cl);
    void inject(MainActivity mainActivity);
    void inject(CityListPresenter presenter);
    void inject(CityDetailPresenter presenter);
    void inject(SearchCityPresenter presenter);
    void inject(MainActivityPresener presenter);

   @ApplicationContext
    Context getContext();
    CityDao getCityDao();

    @Named("citycount")
    long getCityCount();
}
