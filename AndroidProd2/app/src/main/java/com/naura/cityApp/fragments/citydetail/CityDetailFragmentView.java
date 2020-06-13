package com.naura.cityApp.fragments.citydetail;

import com.naura.cityApp.fragments.theatherdata.WeatherData;
import java.util.List;

interface CityDetailFragmentView {
   void setCurrentTemperature(String temperatureNow);
   void setCurrentHumiduty(int airhumidityInt);
   void callTheatherList(List<WeatherData> theatherDays);
}
