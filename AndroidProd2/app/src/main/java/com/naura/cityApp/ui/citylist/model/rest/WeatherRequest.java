package com.naura.cityApp.ui.citylist.model.rest;

import com.google.gson.annotations.SerializedName;

public class WeatherRequest {
    @SerializedName("list")
    private WeatherAddParams[] weatherAddParams;

/*
    @SerializedName("city")
    private City city;

    public City getCity() {
        return city;
    }
*/

    public WeatherAddParams[] getWeatherAddParams() {
        return weatherAddParams;
    }
}
