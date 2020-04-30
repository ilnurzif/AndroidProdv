package com.naura.cityApp.ui.citylist.model.rest;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("name")
    private String cityName;

    public String getCityName() {
        return cityName;
    }
}
