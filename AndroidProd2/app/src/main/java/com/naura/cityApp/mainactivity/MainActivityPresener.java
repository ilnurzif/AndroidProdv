package com.naura.cityApp.mainactivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.naura.cityApp.App;
import com.naura.cityApp.basemodel.CityLoader;
import com.naura.cityApp.basemodel.CityData;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.utility.IImageLoadPresenter;
import com.naura.cityApp.utility.ImageLoadModel;

import javax.inject.Inject;

public class MainActivityPresener implements Observer, IImageLoadPresenter {
    private static MainActivityPresener mainActivityPresener;
    private MainView mainView;
    private ImageLoadModel imageLoadModel;

    @Inject
    Observable observable;

    @Inject
    CityLoader cityLoader;

    @Inject
    Resources resources;


    public MainActivityPresener() {
        imageLoadModel = new ImageLoadModel(this, resources);
        App.getComponent().inject(this);
        if (observable != null)
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
            if (mainView == null) return;
            String title = cityLoader.getDefaultCityName();
            mainView.setToolBarTitle(title);
        }
        if (eventName.equals(EventsConst.LoadWeatherWithCityFinish)) {
            if (mainView == null) return;
            mainView.cityDetailHistoryOpen();
            dataLoad(cityLoader.getDefaultCityName());
        }
        if (eventName.equals(EventsConst.searchFieldVisible)) {
            if (mainView == null) return;
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
        imageLoadModel.startLoadImage(imageUrl);
    }

    @Override
    public void callDrawable(Drawable drawable) {
        mainView.setBackground(drawable);
    }

    public void stopApp() {
        observable.notify(EventsConst.stopApp, null);
    }

    public void startSearchCity(String query) {
        observable.notify(EventsConst.searchCityEvent, query);
    }

}
