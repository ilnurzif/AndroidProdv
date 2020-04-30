package com.naura.cityApp.ui.theatherdata;

import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.ui.citylist.model.database.CityDb;
import com.naura.cityApp.ui.citylist.model.database.CityWeatherDb;
import com.naura.cityApp.ui.theatherdata.TheatherData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// класс для конвертации объектов используемых приложением и объектов базы данных
public class CityDataToCityDbAdapter {
   public static CityData convert(CityDb cityDb) {
       String key=cityDb.cityKey;
       String name=cityDb.cityName;
       String infoUrl=cityDb.infoUrl;
       String imageUrl=cityDb.imageUrl;
       long id=cityDb.id;

       List<TheatherData> theatherDays=new ArrayList<>();

        CityData cityData=new CityData(key, name, theatherDays, infoUrl, imageUrl);
        cityData.setID(id);
        return cityData;
    }

    public static CityDb convert(CityData cityData) {
        String key=cityData.getKey();
        String name=cityData.getName();
        String infoUrl=cityData.getInfoUrl();
        String imageUrl=cityData.getImageUrl();

        CityDb cityDb=new CityDb(key, name, infoUrl, imageUrl);
        return cityDb;
    }

    public static CityWeatherDb convert(TheatherData theatherData, long cityID) {
         float temperature=theatherData.getTemperature();
         int humidity=theatherData.getAirhumidity();
         int pressure=theatherData.getPressure();
         Date date=theatherData.getDay();
         String description=theatherData.getDescription();
         CityWeatherDb cityWeatherDb=new CityWeatherDb(cityID, date, temperature, humidity, pressure, description);
         return cityWeatherDb;
   }

   public static final TheatherData convert(CityWeatherDb cityWeatherDb) {
       long cityID=cityWeatherDb.cityId;
       float temperature=cityWeatherDb.tempereture;
       int humidity= (int) cityWeatherDb.humitity;
       int pressure= (int) cityWeatherDb.pressure;
       Date date=cityWeatherDb.date;
       String description=cityWeatherDb.description;
       TheatherData theatherData=new TheatherData(temperature, pressure, humidity, date, description);
       return theatherData;
   }

   public static final List<CityWeatherDb> convert(List<TheatherData> cityTheatherList, long city_id) {
       List<CityWeatherDb> cityWeatherDbList=new ArrayList<>();
       for (int i = 0; i < cityTheatherList.size(); i++) {
           CityWeatherDb cityWeatherDb=convert(cityTheatherList.get(i),city_id);
           cityWeatherDbList.add(cityWeatherDb);
       }
       return cityWeatherDbList;
   }

   public static final List<TheatherData> convert(List<CityWeatherDb> cityWeatherDbList) {
     List<TheatherData> theatherDataList=new ArrayList<>();
       for (int i = 0; i < cityWeatherDbList.size(); i++) {
           theatherDataList.add(convert(cityWeatherDbList.get(i)));
       }
       return theatherDataList;
   }
}
