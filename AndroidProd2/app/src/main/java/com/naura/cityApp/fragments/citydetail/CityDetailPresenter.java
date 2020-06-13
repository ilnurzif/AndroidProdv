package com.naura.cityApp.fragments.citydetail;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.naura.cityApp.App;
import com.naura.cityApp.basemodel.CityLoader;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.fragments.theatherdata.WeatherData;
import com.naura.cityApp.utility.Utility;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class CityDetailPresenter implements Observer {
    private CityDetailFragmentView cityDetailFragmentView;
    private static CityDetailPresenter cityDetailPresenter;

    @Inject
    Observable observable;

    @Inject
    CityLoader cityLoader;

    private CityDetailPresenter() {
        App.getComponent().inject(this);
        observable.subscribe(this);
    }

    public static CityDetailPresenter getInstance() {
        return cityDetailPresenter = cityDetailPresenter == null ? new CityDetailPresenter() : cityDetailPresenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(CityDetailFragmentView fragmentView) {
        this.cityDetailFragmentView = fragmentView;
        //  cityLoader.startLoad();
        dataLoad(cityLoader.getDefaultCityName(), cityLoader.getDefaultCityWeatherList());
    }

    public void unBind() {
        cityDetailFragmentView = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void dataLoad(String cityName, List<WeatherData> weatherDays) {
        if (cityDetailFragmentView == null) return;
        if (weatherDays == null || weatherDays.size() == 0) {
            cityLoader.startLoadFromInet();
            return;
        }


        Date firstDate = weatherDays.get(0).getDay();
        Boolean dataActual = Utility.checkActualData(firstDate);
        if (!dataActual) {
            cityLoader.startLoadFromInet();
            return;
        }

        cityDetailFragmentView.callTheatherList(weatherDays);

        String temperatureNow = weatherDays.get(0).getFormatedTemperature();
        cityDetailFragmentView.setCurrentTemperature(temperatureNow);

        int airhumidityInt = weatherDays.get(0).getAirhumidity();
        cityDetailFragmentView.setCurrentHumiduty(airhumidityInt);
    }

    public void startCityLoad(Bundle bundle) {
        String mode = "";
        if (bundle != null)
            mode = bundle.getString("mode");

        if (mode.equals("location")) {
            cityLoader.locationLoad();
            return;
        }
        cityLoader.startLoadFromDB();
    }


    public void openCityHistory() {
        observable.notify(EventsConst.openCityHistory, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public <T> void update(String eventName, T val) {
        if (eventName.equals(EventsConst.cityLoadFinish)) {
            if (cityDetailFragmentView == null) return;
            List<WeatherData> cityWeatherList = (List<WeatherData>) val;
            dataLoad(cityLoader.getDefaultCityName(), cityWeatherList);
        }
         if (eventName.equals(EventsConst.LoadWeatherWithCityFinish)) {
            List<WeatherData> weatherDataList = (List<WeatherData>) val;
            if (weatherDataList == null&&cityDetailFragmentView==null) return;
            dataLoad(cityLoader.getDefaultCityName(), weatherDataList);
        }
        if (eventName.equals(EventsConst.addNewCity)) {
          //  if (cityDetailFragmentView!=null)
                cityLoader.startLoadFromDB();
        }
    }
}

