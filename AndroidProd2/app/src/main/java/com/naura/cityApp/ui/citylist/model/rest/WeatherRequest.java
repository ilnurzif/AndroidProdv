package com.naura.cityApp.ui.citylist.model.rest;

import com.google.gson.annotations.SerializedName;

public class WeatherRequest {
    @SerializedName("list")
    private WeatherAddParams[] weatherAddParams;
    public WeatherAddParams[] getWeatherAddParams() {
        return weatherAddParams;
    }
}
