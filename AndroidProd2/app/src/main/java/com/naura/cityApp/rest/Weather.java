package com.naura.cityApp.rest;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("description")
    String description;

    @SerializedName("icon")
    String icon;

    public String getDescription() {
        return description;
    }
    public String getIcon() {return icon;}

}
