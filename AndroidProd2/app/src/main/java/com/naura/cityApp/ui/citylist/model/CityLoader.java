package com.naura.cityApp.ui.citylist.model;

import android.content.Context;

import com.naura.cityApp.App;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.ui.citylist.model.database.CityDb;
import com.naura.cityApp.ui.citylist.model.database.CityWeatherDb;
import com.naura.cityApp.ui.citylist.model.database.DBLoadDataAsyncTask;
import com.naura.cityApp.ui.citylist.model.database.dao.CityDao;
import com.naura.cityApp.ui.citylist.model.rest.ILoadData;
import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.observercode.Observable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class CityLoader {
    private static CityLoader cityLoader;
    protected Context context;
    private List<CityData> cityList;
    private String defaultCityName = "";
    private String defaultKey = "Казань";
    protected Observable observable;
    private ILoadData restLoadData; // для загруки погоды с сервера
    private SharedPrefProp sharedPrefProp = new SharedPrefProp();

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
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
        long cityCount = DaoThreadHelper.getCityCount();
        // если таблица не создавалась заполняем базовыми городами
        if (cityCount == 0) {
            List<TheatherData> kazanTheatherList = new ArrayList<>();
            CityData cityDatak = new CityData(
                    "Казань",
                    "Казань",
                    kazanTheatherList,
                    "https://ru.wikipedia.org/wiki/%D0%9A%D0%B0%D0%B7%D0%B0%D0%BD%D1%8C",
                    "https://images.unsplash.com/photo-1562429645-6711129c79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");

            long id = DaoThreadHelper.getCityID(cityDatak);
            cityDatak.setID(id);

            List<TheatherData> moscowTheatherList = new ArrayList<>();
            CityData cityDatam = new CityData("Москва", "Москва",
                    moscowTheatherList,
                    "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0",
                    "https://images.unsplash.com/photo-1513326738677-b964603b136d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=687&q=80");

            id = DaoThreadHelper.getCityID(cityDatam);
            cityDatak.setID(id);
        }

        cityList = DaoThreadHelper.getCityAll();
    }

    protected CityLoader(Context context) {
        this.context = context;
        observable = Observable.getInstance();
        CallParsingData callParsingData = new CallParsingData();
        restLoadData = new RestLoadData(callParsingData);
        new DBLoadDataAsyncTask(this).execute(callParsingData);
        loaddata();
    }

    protected boolean isCityCached(String cityName) {
        for (CityData cityData : cityList) {
            if (cityData.getName().equals(cityName))
                return true;
        }
        return false;
    }

    private List<TheatherData> getTheatherDataList(final String cityName) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future result = es.submit(new Callable() {
            @Override
            public List<TheatherData> call() throws Exception {
                CityDao cityDao = App.getInstance().getCityDao();
                List<CityWeatherDb> cityWeatherDbList = cityDao.getCityWeatherWithByName(cityName);
                List<TheatherData> theatherDataList = CityDataToCityDbAdapter.convert(cityWeatherDbList);
                return theatherDataList;
            }
        });
        try {
            return (List<TheatherData>) result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TheatherData> getAllWeatherWithCity(String cityName) {
        List<TheatherData> theatherDataList = getTheatherDataList(cityName);
        return theatherDataList;
    }

    public void startLoad() {
        new DBLoadDataAsyncTask(this).execute(new CallParsingData()); // загружаем ранее сохраненные данные о погоде
        restLoadData.request(getDefaultCityName()); // и если сервер доступен обновляем
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

    class CallParsingData implements ICallData {
        @Override
        public void execute(List<TheatherData> cityTheatherList, String cityName, boolean saveData) {
            setDefaultKey(cityName);
            Observable observable = Observable.getInstance();

            CityData cityData = new CityData(cityName, cityName,
                    cityTheatherList);
            cityData.setFavoriteCity(false);

            if (saveData) {
                boolean cityCached = isCityCached(cityName);
                int res = DaoThreadHelper.insertDb(cityName, cityData, cityTheatherList, cityCached);
            }

            if (!isCityCached(cityName)) {
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
