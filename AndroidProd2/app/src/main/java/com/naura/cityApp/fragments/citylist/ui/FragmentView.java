package com.naura.cityApp.fragments.citylist.ui;

import com.naura.cityApp.fragments.citydetail.CityData;

import java.util.List;

public interface FragmentView {
    void setCityDataList(List<CityData> cityList);
    void updateCityList(List<CityData> cityList);
    void setFavorCityList(List<CityData> favorCityList);
}
