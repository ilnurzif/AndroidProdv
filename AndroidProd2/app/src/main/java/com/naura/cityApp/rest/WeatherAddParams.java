package com.naura.cityApp.rest;

import com.google.gson.annotations.SerializedName;

public class WeatherAddParams {
    @SerializedName("dt_txt")
    private String dt_txt;

    @SerializedName("main")
    private MainWeatherParams mainWeatherParams;

    @SerializedName("weather")
    private Weather[] weather;

    public Weather[] getWeather() {
        return weather;
    }

    public MainWeatherParams getMainWeatherParams() {
        return mainWeatherParams;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
