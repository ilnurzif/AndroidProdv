package com.naura.cityApp.fragments.theatherdata;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherData implements Serializable {
    private float temperature;
    private Date date;
    private int airhumidity;
    private int pressure;
    private String description;
    private long ID;

    public float getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public int getAirhumidity() {
        return airhumidity;
    }

    public int getPressure() {
        return pressure;
    }

    public WeatherData(float temperature, int pressure, int airhumidity, Date date, String description) {
        this.temperature = temperature;
        this.date = date;
        this.airhumidity = airhumidity;
        this.description=description;
        this.pressure=pressure;
    }


    public Date getDay() {
        return date;
    }

    public String getDateStr() {
        SimpleDateFormat resDate = new SimpleDateFormat("E  dd.MM");
        return resDate.format(date);
    }

    public String getFormatedTemperature() {
        String formTemp=String.format(Locale.getDefault(), "%.0f", temperature) + "\u2103";
        return formTemp;
    }

    public String getFormatedHumitity() {
       String fornHum= Integer.toString(airhumidity)+"%";
       return fornHum;
    }

    public String getFormatedPressure() {
        String formPress= Integer.toString(pressure)+"hPa";
        return formPress;
    }

    public void setID(long id) {
        this.ID=id;
    }

    public long getID() {
        return ID;
    }
}