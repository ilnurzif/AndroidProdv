package com.naura.cityApp.cityloader.rest;

import com.google.gson.annotations.SerializedName;

public class MainWeatherParams {
    @SerializedName("temp")
    private float temperature;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("pressure")
    private int pressure;

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public float getTemperature() {
        return temperature;
    }
}
