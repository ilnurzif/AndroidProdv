package com.naura.cityApp.rest;

import com.naura.cityApp.App;
import com.naura.cityApp.basemodel.ICallData;
import com.naura.cityApp.basemodel.WeatherData;
import com.naura.cityApp.utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


import android.os.Build;

import androidx.annotation.RequiresApi;

import javax.inject.Inject;
import javax.inject.Named;

public class RestLoadData implements ILoadData {
    private int cnt = 16;
    private String lang = "ru";
    private String units = "metric";
    private ICallData callData;
    private String cityName;

    @Inject
    OpenWeather openWeather;

    @Inject
    @Named("keyApi")
    String keyApi;

    public RestLoadData(ICallData callData) {
        this.callData = callData;
        App.getComponent().inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void request(final String cityName) {
        Disposable disposable = openWeather.loadWeather(cityName, cnt, lang, keyApi, units)
                .map(cityWeather -> {
                    List<WeatherData> cityWeatherList = new ArrayList<>();
                    WeatherAddParams[] weatherAddParams = cityWeather.getWeatherAddParams();
                    WeatherData weatherData = respToTheatherData(weatherAddParams[0]);
                    cityWeatherList.add(weatherData);
                    for (int i = 1; i < weatherAddParams.length; i++) {
                        Date prevDate = weatherData.getDay();
                        weatherData = respToTheatherData(weatherAddParams[i]);
                        if (Utility.compareDates(prevDate, weatherData.getDay()) != 0) {
                            cityWeatherList.add(weatherData);
                        }
                    }
                    return cityWeatherList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<WeatherData>>() {
                    @Override
                    public void onSuccess(List<WeatherData> cityWeatherList) {
                        callData.callWeatherList(cityWeatherList, cityName, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String str=e.getLocalizedMessage();
                        String detail=e.getMessage();
                        callData.errorTextReturn(e.getMessage());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void request(final double latitude, final double longitude) {
        Disposable disposable = openWeather.loadWeather(cnt, lang, keyApi, units, latitude, longitude)
                .map(cityWeather -> {
                    List<WeatherData> cityWeatherList = new ArrayList<>();
                    WeatherAddParams[] weatherAddParams = cityWeather.getWeatherAddParams();
                    WeatherData weatherData = respToTheatherData(weatherAddParams[0]);
                    cityWeatherList.add(weatherData);
                    for (int i = 1; i < weatherAddParams.length; i++) {
                        Date prevDate = weatherData.getDay();
                        weatherData = respToTheatherData(weatherAddParams[i]);
                        if (Utility.compareDates(prevDate, weatherData.getDay()) != 0) {
                            cityWeatherList.add(weatherData);
                        }
                    }
                    cityName = cityWeather.getCity().getCityName();
                    return cityWeatherList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<WeatherData>>() {
                    @Override
                    public void onSuccess(List<WeatherData> cityWeatherList) {
                        callData.callAllWeatherList(cityWeatherList, cityName);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callData.errorTextReturn(e.getMessage());
                    }
                });
    }

    private WeatherData respToTheatherData(WeatherAddParams weatherAddParams) {
        try {
            float temper = weatherAddParams.getMainWeatherParams().getTemperature();
            int humidity = weatherAddParams.getMainWeatherParams().getHumidity();
            int pressure = weatherAddParams.getMainWeatherParams().getPressure();
            String dt_txt = weatherAddParams.getDt_txt();
            Weather[] weather = weatherAddParams.getWeather();
            String description = weather[0].getDescription();
            String iconUrl = weather[0].getIcon();

            Date tempDate = getFormatedDate(dt_txt);
            return new WeatherData(temper, pressure, humidity, tempDate, description, iconUrl);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date getFormatedDate(String dt_txt) throws ParseException {
        String dateS = dt_txt;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(dateS);
    }

}
