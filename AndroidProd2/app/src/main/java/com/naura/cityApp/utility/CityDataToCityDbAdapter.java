package com.naura.cityApp.utility;

import com.naura.cityApp.basemodel.CityData;
import com.naura.cityApp.database.CityDb;
import com.naura.cityApp.database.CityWeatherDb;
import com.naura.cityApp.basemodel.WeatherData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// класс для конвертации объектов используемых приложением и объектов базы данных
public class CityDataToCityDbAdapter {
    public static CityData convert(CityDb cityDb) {
        String key = cityDb.cityKey;
        String name = cityDb.cityName;
        String infoUrl = cityDb.infoUrl;
        String imageUrl = cityDb.imageUrl;
        Boolean isFavorite = cityDb.favorite;
        long id = cityDb.id;
        List<WeatherData> theatherDays = new ArrayList<>();
        CityData cityData = new CityData(key, name, theatherDays, infoUrl, imageUrl);
        cityData.setFavoriteCity(isFavorite);
        cityData.setID(id);
        return cityData;
    }

    public static CityDb convert(CityData cityData) {
        long id = cityData.getID();
        String key = cityData.getKey();
        String name = cityData.getName();
        String infoUrl = cityData.getInfoUrl();
        String imageUrl = cityData.getImageUrl();
        Boolean isFavorite = cityData.isFavoriteCity();

        CityDb cityDb = new CityDb(key, name, infoUrl, imageUrl, isFavorite);
        cityDb.id = id;
        return cityDb;
    }

    public static CityWeatherDb convert(WeatherData weatherData, long cityID) {
        float temperature = weatherData.getTemperature();
        int humidity = weatherData.getAirhumidity();
        int pressure = weatherData.getPressure();
        Date date = weatherData.getDay();
        String description = weatherData.getDescription();
        String iconUrl = weatherData.getIconUrl();
        CityWeatherDb cityWeatherDb = new CityWeatherDb(cityID, date, temperature, humidity, pressure, description, iconUrl);
        return cityWeatherDb;
    }

    public static final WeatherData convert(CityWeatherDb cityWeatherDb) {
        long cityID = cityWeatherDb.cityId;
        float temperature = cityWeatherDb.tempereture;
        int humidity = (int) cityWeatherDb.humitity;
        int pressure = (int) cityWeatherDb.pressure;
        Date date = cityWeatherDb.date;
        String description = cityWeatherDb.description;
        String iconUrl = cityWeatherDb.iconurl;
        WeatherData weatherData = new WeatherData(temperature, pressure, humidity, date, description, iconUrl);
        return weatherData;
    }

    public static final List<CityWeatherDb> convert(List<WeatherData> cityTheatherList, long city_id) {
        List<CityWeatherDb> cityWeatherDbList = new ArrayList<>();
        for (int i = 0; i < cityTheatherList.size(); i++) {
            CityWeatherDb cityWeatherDb = convert(cityTheatherList.get(i), city_id);
            cityWeatherDbList.add(cityWeatherDb);
        }
        return cityWeatherDbList;
    }

    public static final List<WeatherData> convert(List<CityWeatherDb> cityWeatherDbList) {
        List<WeatherData> weatherDataList = new ArrayList<>();
        for (int i = 0; i < cityWeatherDbList.size(); i++) {
            weatherDataList.add(convert(cityWeatherDbList.get(i)));
        }
        return weatherDataList;
    }
}
