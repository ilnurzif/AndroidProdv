package com.naura.cityApp.mainactivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.naura.cityApp.App;
import com.naura.cityApp.basemodel.CityLoader;
import com.naura.cityApp.fragments.citydetail.CityData;
import com.naura.cityApp.location.CityLocation;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;

import javax.inject.Inject;

public class MainActivityPresener implements Observer, IMainPresenter {
    private static MainActivityPresener mainActivityPresener;
    private MainView mainView;
    private MainActivityModel mainActivityModel;

    @Inject
    Observable observable;

    @Inject
    CityLoader cityLoader;

    @Inject
    Resources resources;


    public MainActivityPresener() {
        mainActivityModel = new MainActivityModel(this, resources);
        App.getComponent().inject(this);
        observable.subscribe(this);
    }

    public static MainActivityPresener getInstance() {
        return mainActivityPresener = mainActivityPresener == null ? new MainActivityPresener() : mainActivityPresener;
    }

    @Override
    public <T> void update(String eventName, T val) {
        if (eventName.equals(EventsConst.selectCityEvent)) {
            dataLoad((String) val);
            if (mainView == null) return;
            String title = cityLoader.getDefaultCityName();
            mainView.setToolBarTitle(title);
        }

        if (eventName.equals(EventsConst.selectCityLoad)) {
            dataLoad((String) val);
            if (mainView == null) return;
            mainView.cityDetailHistoryOpen();
            String title = cityLoader.getDefaultCityName();
            mainView.setToolBarTitle(title);
        }

        if (eventName.equals(EventsConst.openCityHistory)) {
            if (mainView == null) return;
            mainView.citySearchHistoryOpen();
            String title = cityLoader.getDefaultCityName() + " - история просмотров";
            mainView.setToolBarTitle(title);
        }

        if (eventName.equals(EventsConst.mapSelectEvent)) {
            dataLoad(cityLoader.getDefaultCityName());
            if (mainView == null) return;
            mainView.cityDetailHistoryOpen();
            String title = cityLoader.getDefaultCityName();
            mainView.setToolBarTitle(title);
        }

        if (eventName.equals(EventsConst.cityLoadFinish)) {
            String title = cityLoader.getDefaultCityName();
            mainView.setToolBarTitle(title);
        }
        if (eventName.equals(EventsConst.LoadWeatherWithCityFinish)) {
            if (mainView == null) return;
            mainView.cityDetailHistoryOpen();
            dataLoad(cityLoader.getDefaultCityName());
        }
        if (eventName.equals(EventsConst.searchFieldVisible)) {
            Boolean visible = (Boolean) val;
            mainView.setSearchFieldVisible(visible);
        }
    }

    public void bind(MainView mainView) {
        this.mainView = mainView;
    }

    public void unBind() {
        if (mainView != null)
            mainView = null;
    }

    public void startDataLoad() {
        dataLoad(cityLoader.getDefaultCityName());
    }

    public void dataLoad(String cityName) {
        CityData cityData = cityLoader.getCity(cityName);
        if (mainView == null || cityData == null) return;
        mainView.setToolBarTitle(cityName);
        if (cityData == null) return;
        String imageUrl = cityData.getImageUrl();
        mainActivityModel.setBackGround(imageUrl);
    }

    @Override
    public void setBackground(Drawable drawable) {
        mainView.setBackground(drawable);
    }

    public void stopApp() {
        observable.notify(EventsConst.stopApp, null);
    }

    public void startSearchCity(String query) {
        observable.notify(EventsConst.searchCityEvent, query);
    }

}
