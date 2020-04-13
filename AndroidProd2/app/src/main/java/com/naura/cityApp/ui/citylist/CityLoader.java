package com.naura.cityApp.ui.citylist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.observercode.Observable;
import com.naura.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CityLoader {
    protected Context context;
    protected List<CityData> cityList;
    private String defaultCityName = "";
    private String defaultKey = "";
    private static CityLoader cityLoader;
    protected Observable observable;
    protected String searchCityName="";

    public String getDefaultKey() {
        return defaultKey;
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    private void loaddata() {
        context.getString(R.string.Moscow);

        cityList = new ArrayList<>();

        List<TheatherData> kazanTheatherList = new ArrayList<>();
        cityList.add(new CityData(
                "Казань",
                "Казань",
                kazanTheatherList,
                ResToBitmap(R.drawable.kazanvertical),
                ResToBitmap(R.drawable.kazanhorizontal),
                ResToBitmap(R.drawable.kazan_small),
                "https://ru.wikipedia.org/wiki/%D0%9A%D0%B0%D0%B7%D0%B0%D0%BD%D1%8C"));

        List<TheatherData> moscowTheatherList = new ArrayList<>();
        cityList.add(new CityData("Москва", "Москва",
                moscowTheatherList,
                ResToBitmap(R.drawable.moscowsity),
                ResToBitmap(R.drawable.moscowhorizont),
                ResToBitmap(R.drawable.kazan_small),
                "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0"));
    }


    protected CityLoader(Context context) {
        this.context = context;
        observable = Observable.getInstance();
    }

    protected boolean isCityCached(String cityName) {
        for (CityData cityData: cityList) {
            if (cityData.getName().equals(cityName))
                return true;
        }
        return false;
    }

    public void startLoad() {
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

    public List<TheatherData> getTheatherData(Context context, String cityname) {
        CityData cityData = getCity(cityname);
        return (cityData == null) ? null : cityData.getTheatherDays();
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

    }
}
