package com.naura.cityApp.basemodel;

import com.naura.cityApp.fragments.theatherdata.WeatherData;
import java.util.List;

public interface ICallData {
    public String errorTextReturn(String errMsg);

    void callWeatherList(List<WeatherData> cityTheatherList, String cityName, boolean b);

    void callAllWeatherList(List<WeatherData> cityTheatherList, String cityName);
}

