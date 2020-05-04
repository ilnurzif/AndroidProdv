package com.naura.cityApp.cityloader.rest;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("description")
    String description;

    public String getDescription() {
        return description;
    }
}
