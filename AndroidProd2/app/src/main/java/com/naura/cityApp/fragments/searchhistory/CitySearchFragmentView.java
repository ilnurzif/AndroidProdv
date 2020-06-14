package com.naura.cityApp.fragments.searchhistory;

import com.naura.cityApp.basemodel.WeatherData;

import java.util.List;

interface CitySearchFragmentView {
    void loadWeather(List<WeatherData> weatherDataList);
}
