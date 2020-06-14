package com.naura.cityApp.fragments.citydetail;

import com.naura.cityApp.basemodel.WeatherData;
import java.util.List;

interface CityDetailFragmentView {
   void setCurrentTemperature(String temperatureNow);
   void setCurrentHumiduty(String airhumidityStr);

   void callTheatherList(List<WeatherData> theatherDays);
   void setCurrentPressure(String pressureStr);
   void setCurrentWeatherDesc(String weatherDescription);
   void callWeatherIcon(String deawableName);
    void callCurrentDate(String currentDate);
}
