package com.naura.cityApp.fragments.searchhistory;

import com.naura.cityApp.fragments.theatherdata.WeatherData;

import java.util.List;

interface CitySearchFragmentView {
    void loadWeather(List<WeatherData> weatherDataList);
}
