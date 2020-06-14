package com.naura.cityApp.basemodel;

import java.util.List;

public class CityData {
    private String key;
    private String name;
    private List<WeatherData> weatherDays;
    private String infoUrl;
    private boolean favoriteCity = true;
    private String imageUrl;
    private long ID;

    public boolean isFavoriteCity() {
        return favoriteCity;
    }

    public void setFavoriteCity(boolean favoriteCity) {
        this.favoriteCity = favoriteCity;
    }

    public CityData(String key,
                    String name,
                    List<WeatherData> weatherDays,
                    String infoUrl,
                    String imageUrl) {

        this.key = key;
        this.name = name;
        this.weatherDays = weatherDays;
        this.infoUrl = infoUrl;
        this.imageUrl = imageUrl;
    }

    public CityData(String key,
                    String name,
                    List<WeatherData> weatherDays) {

        this.key = key;
        this.name = name;
        this.weatherDays = weatherDays;
        this.infoUrl = "";
        this.imageUrl = "";
    }

    public void setWeatherDays(List<WeatherData> weatherDays) {
        this.weatherDays=weatherDays;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<WeatherData> getWeatherDays() {
        return weatherDays;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setID(long id) {
        this.ID=id;
    }

    public long getID() {
        return ID;
    }
}
