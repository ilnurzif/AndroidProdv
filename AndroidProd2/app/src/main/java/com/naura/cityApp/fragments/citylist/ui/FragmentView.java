package com.naura.cityApp.fragments.citylist.ui;

import com.naura.cityApp.basemodel.CityData;

import java.util.List;

public interface FragmentView {
    void setCityDataList(List<CityData> cityList);
    void updateCityList(List<CityData> cityList);
    void setFavorCityList(List<CityData> favorCityList);
    void startCityEvent(String cityName);
    void CityFoundErrorMsg(String errMsg);
}
