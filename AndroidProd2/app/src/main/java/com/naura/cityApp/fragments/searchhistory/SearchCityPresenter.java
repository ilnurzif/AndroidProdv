package com.naura.cityApp.fragments.searchhistory;

import com.naura.cityApp.App;
import com.naura.cityApp.basemodel.CityLoader;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.fragments.theatherdata.WeatherData;

import java.util.List;

import javax.inject.Inject;

public class SearchCityPresenter implements Observer {
    private static SearchCityPresenter searchCityPresenter;
    CitySearchFragmentView citySearchFragmentView;

    @Inject
    Observable observable;

    @Inject
    CityLoader cityLoader;

    private SearchCityPresenter() {
        App.getComponent().inject(this);
        observable.subscribe(this);
    }

    public static SearchCityPresenter getInstance() {
        return searchCityPresenter = searchCityPresenter == null ? new SearchCityPresenter() : searchCityPresenter;
    }

    public void bind(CitySearchFragmentView fragmentView) {
        this.citySearchFragmentView = fragmentView;
    }

    public void unBind() {
        citySearchFragmentView = null;
    }

    @Override
    public <T> void update(String eventName, T val) {
        if (eventName.equals(EventsConst.LoadWeatherWithCityFinish)) {
            List<WeatherData> weatherDataList = (List<WeatherData>) val;
            if (weatherDataList == null) return;
            citySearchFragmentView.loadWeather(weatherDataList);
        }
    }

    public void StartLoadWeatherWithCity() {
        cityLoader.StartLoadWeatherWithCity();
    }
}
