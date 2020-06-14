package com.naura.cityApp.rest;

import com.google.gson.annotations.SerializedName;

public class WeatherRequest {
    @SerializedName("list")
    private WeatherAddParams[] weatherAddParams;
    public WeatherAddParams[] getWeatherAddParams() {
        return weatherAddParams;
    }
    @SerializedName("city")
    private City city;
    public City getCity() {
        return city;
    }
}
