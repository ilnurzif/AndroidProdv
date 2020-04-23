package com.naura.cityApp.ui.citylist.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.ui.citylist.model.rest.ILoadData;
import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.observercode.Observable;
import com.naura.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class CityLoader {
    private static CityLoader cityLoader;
    protected Context context;
    private List<CityData> cityList;
    private String defaultCityName = "";
    private String defaultKey = "Казань";
    protected Observable observable;
    ILoadData restLoadData;

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    public static CityLoader getInstance(Context context) {
        if (cityLoader == null) {
            cityLoader = new CityLoader(context);
        }
        return cityLoader;
    }

    private void loaddata() {
        cityList = new ArrayList<>();

        List<TheatherData> kazanTheatherList = new ArrayList<>();
        cityList.add(new CityData(
                "Казань",
                "Казань",
                kazanTheatherList,
                "https://ru.wikipedia.org/wiki/%D0%9A%D0%B0%D0%B7%D0%B0%D0%BD%D1%8C",
                "https://images.unsplash.com/photo-1562429645-6711129c79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"));

        List<TheatherData> moscowTheatherList = new ArrayList<>();
        cityList.add(new CityData("Москва", "Москва",
                moscowTheatherList,
                "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0",
                "https://images.unsplash.com/photo-1513326738677-b964603b136d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=687&q=80"));
    }

    protected CityLoader(Context context) {
        this.context = context;
        observable = Observable.getInstance();
        restLoadData=new RestLoadData(new CallParsingData());
    }

    protected boolean isCityCached(String cityName) {
        for (CityData cityData : cityList) {
            if (cityData.getName().equals(cityName))
                return true;
        }
        return false;
    }

    public void startLoad() {
        restLoadData.request(getDefaultCityName());
    }

    protected Bitmap ResToBitmap(int resid) {
        return BitmapFactory.decodeResource(context.getResources(), resid);
    }

    protected CityData findCachedCity(String cityname) {
        for (CityData cd : cityList) {
            if (cd.getName().equals(cityname))
                return cd;
        }
        return null;
    }

    public void SetlikeCity(String cityName) {
        for (CityData cityData : cityList) {
            if (cityData.getName().equals(cityName))
                cityData.setFavoriteCity(!cityData.isFavoriteCity());
        }
    }

    public CityData getCity(String cityname) {
        if (cityList == null) {
            cityList = new ArrayList<>();
            loaddata();
        }
        return findCachedCity(cityname);
    }

    public List<CityData> getCityList() {
        if (cityList == null) {
            cityList = new ArrayList<>();
            loaddata();
        }
        return cityList;
    }

    public List<CityData> getFavorCityList() {
        getCityList();
        List<CityData> favorCityList = new ArrayList<>();
        for (CityData cityData :
                cityList) {
            if (cityData.isFavoriteCity())
                favorCityList.add(cityData);
        }
        return favorCityList;
    }

    public String getDefaultCityName() {
        if (defaultCityName.equals(""))
            defaultCityName = "Казань";
        return defaultCityName;
    }

    public void setDefaultCityName(String cityName) {
        defaultCityName = cityName;
        for (CityData cityData : cityList) {
            if (cityData.getName().equals(cityName))
                defaultKey = cityData.getKey();
        }
    }

    public void searchCity(String cityName) {
        setDefaultCityName(cityName);
        restLoadData.request(getDefaultCityName());
    }

    class CallParsingData implements ICallData {
        @Override
        public void execute(List<TheatherData> cityTheatherList, String cityName) {
            setDefaultKey(cityName);
            Observable observable = Observable.getInstance();

            if (!isCityCached(cityName)) {
                CityData cityData = new CityData(cityName, cityName,
                        cityTheatherList,
                        "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0",
                        "");
                cityData.setFavoriteCity(false);
                cityList.add(cityData);
                observable.notify(EventsConst.addNewCity, cityData);
            } else {
                observable.notify(EventsConst.cityLoadFinish, cityTheatherList);
            }
        }

        @Override
        public String errorTextReturn(String errMsg) {
            return null;
        }
    }
}
