package com.naura.cityApp.basemodel;

import java.util.List;

public interface ICallData {
    public void errorTextReturn(String errMsg);
    void callWeatherList(List<WeatherData> cityTheatherList, String cityName, boolean b);
    void callAllWeatherList(List<WeatherData> cityTheatherList, String cityName);

    void deleteComplete();
}

