package com.naura.cityApp.basemodel;

import com.naura.cityApp.App;
import com.naura.cityApp.database.CityDb;
import com.naura.cityApp.location.CityLocation;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.rest.ILoadData;
import com.naura.cityApp.rest.RestLoadData;
import com.naura.cityApp.utility.CityDataToCityDbAdapter;
import com.naura.cityApp.utility.SharedPrefProp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CityLoader implements ICallData {
    private static CityLoader cityLoader;
    private static final int dayCount = 10;
    private List<CityData> cityList;
    private String defaultCityName = "";
    private String defaultKey = "Казань";
    private ILoadData restLoadData;
    private SharedPrefProp sharedPrefProp;
    private CityLocation cityLocation;
    private List<WeatherData> defaultCityWeatherList;

    @Inject
    Observable observable;

    public void setDefaultKey(String val) {
        this.defaultKey = val;
        this.defaultCityName = val;
    }

    public static CityLoader getInstance() {
        if (cityLoader == null) {
            cityLoader = new CityLoader();
        }
        return cityLoader;
    }

    private void loaddata() {
        long cityCount = App.getComponent().getCityCount();
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

            long id = RoomRxHelper.getCityID(cityDatak);
            cityDatak.setID(id);

            List<WeatherData> moscowTheatherList = new ArrayList<>();
            CityData cityDatam = new CityData("Москва", "Москва",
                    moscowTheatherList,
                    "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0",
                    "https://images.unsplash.com/photo-1513326738677-b964603b136d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=687&q=80");

            id = RoomRxHelper.getCityID(cityDatam);
            cityDatam.setID(id);

            List<WeatherData> piterTheatherList = new ArrayList<>();
            CityData cityDatap = new CityData("Санкт-Петербург", "Санкт-Петербург",
                    piterTheatherList,
                    "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0",
                    "https://images.unsplash.com/photo-1554202218-20ee1af0fb17?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80");

            id = RoomRxHelper.getCityID(cityDatap);
            cityDatap.setID(id);

            List<WeatherData> sochiTheatherList = new ArrayList<>();
            CityData cityDatas = new CityData("Сочи", "Сочи",
                    sochiTheatherList,
                    "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0",
                    "https://images.unsplash.com/photo-1574617935147-5f48771a6bc3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80");

            id = RoomRxHelper.getCityID(cityDatas);
            cityDatas.setID(id);
        }

        cityList = new ArrayList<>();
        RoomRxHelper.callCityAll(cityList);
    }

    protected CityLoader() {
        sharedPrefProp = new SharedPrefProp();
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
        RoomRxHelper.callWeatherCity(this, getDefaultCityName(), dayCount);
    }

    public void startLoadFromInet() {
        restLoadData.request(getDefaultCityName());
    }

    public void locationLoad() {
        cityLocation = CityLocation.getInstance();
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
        RoomRxHelper.updateCity(cityDb);
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
                defaultCityName = sharedPrefProp.loadDefaultCity();
            return defaultCityName;
        } catch (Exception e) {
        }
        return "Казань";
    }

    public void setDefaultCityName(String cityName) {
        defaultCityName = cityName;
        defaultCityWeatherList = null;
        sharedPrefProp.saveDefaultCity(defaultCityName);
        for (CityData cityData : cityList) {
            if (cityData.getName().equals(cityName)) {
                defaultKey = cityData.getKey();
                defaultCityWeatherList = cityData.getWeatherDays();
            }
        }
    }

    public void searchCity(String cityName) {
        setDefaultCityName(cityName);
        restLoadData.request(getDefaultCityName());
    }

    public void StartLoadWeatherWithCity() {
        RoomRxHelper.callWeatherDataList(this, getDefaultCityName());
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
            long ins_id = RoomRxHelper.insertDb(cityName, cityData, cityWeatherList, cityCached);
            cityData.setID(ins_id);
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
