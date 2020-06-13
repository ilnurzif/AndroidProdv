package com.naura.cityApp.basemodel;

import android.content.Context;

import com.naura.cityApp.App;
import com.naura.cityApp.database.CityDb;
import com.naura.cityApp.location.CityLocation;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.rest.ILoadData;
import com.naura.cityApp.fragments.citydetail.CityData;
import com.naura.cityApp.fragments.theatherdata.WeatherData;
import com.naura.cityApp.rest.RestLoadData;
import com.naura.cityApp.utility.CityDataToCityDbAdapter;
import com.naura.cityApp.utility.SharedPrefProp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CityLoader implements ICallData {
    private static CityLoader cityLoader;
    private static final int dayCount = 3;
    protected Context context;
    private List<CityData> cityList;
    private String defaultCityName = "";
    private String defaultKey = "Казань";
    private ILoadData restLoadData;
    private SharedPrefProp sharedPrefProp = new SharedPrefProp();
    private CityLocation cityLocation;
    private List<WeatherData> defaultCityWeatherList;

    @Inject
    Observable observable;


    public void setDefaultKey(String val) {
        this.defaultKey = val;
        this.defaultCityName = val;
    }

    public static CityLoader getInstance(Context context) {
        if (cityLoader == null) {
            cityLoader = new CityLoader(context);
        }
        return cityLoader;
    }

    public static CityLoader getInstance() {
        return cityLoader;
    }


    private void loaddata() {
        long cityCount = App.getComponent().getCityCount();//DaoThreadHelper.getCityCount();
        // если таблица не создавалась заполняем базовыми городами
        if (cityCount == 0) {
            List<WeatherData> kazanTheatherList = new ArrayList<>();
            CityData cityDatak = new CityData(
                    "Казань",
                    "Казань",
                    kazanTheatherList,
                    "https://ru.wikipedia.org/wiki/%D0%9A%D0%B0%D0%B7%D0%B0%D0%BD%D1%8C",
                    "https://images.unsplash.com/photo-1562429645-6711129c79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            );

            long id = DaoThreadHelper.getCityID(cityDatak);
            cityDatak.setID(id);

            List<WeatherData> moscowTheatherList = new ArrayList<>();
            CityData cityDatam = new CityData("Москва", "Москва",
                    moscowTheatherList,
                    "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0",
                    "https://images.unsplash.com/photo-1513326738677-b964603b136d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=687&q=80");

            id = DaoThreadHelper.getCityID(cityDatam);
            cityDatak.setID(id);
        }

        cityList = new ArrayList<>();
        DaoThreadHelper.callCityAll(cityList);
    }

    protected CityLoader(Context context) {
        this.context = context;
        observable = Observable.getInstance();
        restLoadData = new RestLoadData(this);
        loaddata();
    }

    protected boolean isCityCached(String cityName) {
        try {
            for (CityData cityData : cityList) {
                if (cityData.getName().equals(cityName))
                    return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void startLoadFromDB() {
        DaoThreadHelper.callWeatherCity(this, getDefaultCityName(), dayCount);
    }

    public void startLoadFromInet() {
        restLoadData.request(getDefaultCityName());
    }

    public void locationLoad() {
        cityLocation = CityLocation.getInstance(context);
        double latitude = cityLocation.getLatitude();
        double longitude = cityLocation.getLongitude();
        restLoadData.request(latitude, longitude);
    }

    public void locationLoad(double latitude, double longitude) {
        restLoadData.request(latitude, longitude);
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

    public void favoriteStateUpdate(String cityName) {
        CityData cityData = getCity(cityName);
        CityDb cityDb = CityDataToCityDbAdapter.convert(cityData);
        DaoThreadHelper.updateCity(cityDb);
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
        try {
            if (defaultCityName.equals(""))
                defaultCityName = sharedPrefProp.loadDefaultCity(context);
            return defaultCityName;
        } catch (Exception e) {
        }
        return "Казань";
    }

    public void setDefaultCityName(String cityName) {
        defaultCityName = cityName;
        sharedPrefProp.saveDefaultCity(context, defaultCityName);
        for (CityData cityData : cityList) {
            if (cityData.getName().equals(cityName))
                defaultKey = cityData.getKey();
        }
    }

    public void searchCity(String cityName) {
        setDefaultCityName(cityName);
        restLoadData.request(getDefaultCityName());
    }

    public void StartLoadWeatherWithCity() {
        DaoThreadHelper.callWeatherDataList(this, getDefaultCityName());
    }

    @Override
    public void callAllWeatherList(List<WeatherData> cityWeatherList, String cityName) {
        if (cityWeatherList == null) return;
        defaultCityWeatherList = cityWeatherList;
        callWeatherList(cityWeatherList, cityName, true);
        observable.notify(EventsConst.LoadWeatherWithCityFinish, cityWeatherList);
    }

    // если saveData=true возвращаем сохраненную ранее погоду в горозе за x дней из базы данных иначе данные пришли от Retrofit
    @Override
    public void callWeatherList(List<WeatherData> cityWeatherList, String cityName, boolean saveData) {
        setDefaultKey(cityName);
        defaultCityWeatherList = cityWeatherList;

        CityData cityData = new CityData(cityName, cityName, cityWeatherList);
        cityData.setFavoriteCity(false);

        boolean cityCached = isCityCached(cityName);
        if (saveData) {
            int res = DaoThreadHelper.insertDb(cityName, cityData, cityWeatherList, cityCached);
        }

        if (!cityCached) {
            cityList.add(cityData);
            observable.notify(EventsConst.addNewCity, cityData);
        } else {
            if (cityWeatherList != null && cityWeatherList.size() > 0)
                observable.notify(EventsConst.cityLoadFinish, cityWeatherList);
        }
    }

    @Override
    public String errorTextReturn(String errMsg) {
        return null;
    }

    public List<WeatherData> getDefaultCityWeatherList() {
        return defaultCityWeatherList;
    }

}
