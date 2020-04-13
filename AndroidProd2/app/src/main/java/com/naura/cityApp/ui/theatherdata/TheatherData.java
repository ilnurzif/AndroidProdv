package com.naura.cityApp.ui.theatherdata;

import java.io.Serializable;

public class TheatherData implements Serializable {
    private String temperature;
    private String date;
    private String airhumidity;
    private int theathericon;
    private String description;

    public String getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public String getAirhumidity() {
        return airhumidity;
    }

    public TheatherData(String temperature, String pressure, String airhumidity, String date, String description, int theathericon) {
        this.temperature = temperature;
        this.date = date;
        this.theathericon = theathericon;
        this.airhumidity = airhumidity;
        this.description=description;
    }

    public String getDay() {
        return date;
    }

    public int getTheathericon() {
        return theathericon;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWeekday(String weekday) {
        this.date = weekday;
    }

    public void setTheatherIcon(int theatherIcon) {
        this.theathericon = theatherIcon;
    }
}