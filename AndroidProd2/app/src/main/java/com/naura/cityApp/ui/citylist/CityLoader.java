package com.naura.cityApp.ui.citylist;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.naura.cityApp.observercode.BroadcastEvent;
import com.naura.cityApp.observercode.EventsConst;
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
    private static LoadDataService.LoadDataServiceBinder loadDataServiceBinder;
    private boolean isBound;
    private OneTimeWorkRequest workRequest;

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
        context.registerReceiver(loadDataFinishedReceiver, new IntentFilter(BroadcastEvent.BROADCAST_ACTION_LOADCITYFINISHED));
        Intent intent = new Intent(context, LoadDataService.class);
        intent.putExtra("cityName", defaultKey);
        context.bindService(intent, loadDataServiceConnection, Context.BIND_AUTO_CREATE);
    }

    protected boolean isCityCached(String cityName) {
        for (CityData cityData : cityList) {
            if (cityData.getName().equals(cityName))
                return true;
        }
        return false;
    }

    public void startLoad() {
        if (loadDataServiceBinder != null)
            loadDataServiceBinder.startLoaddata(defaultCityName);
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
        loadDataServiceBinder.startLoaddata(cityName);
    }

    public void stopApp() {
        context.unregisterReceiver(loadDataFinishedReceiver);
        if (isBound) {
            context.unbindService(loadDataServiceConnection);
        }
    }

    private ServiceConnection loadDataServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (loadDataServiceBinder == null) {
                loadDataServiceBinder = (LoadDataService.LoadDataServiceBinder) service;
                loadDataServiceBinder.startLoaddata(getDefaultCityName());
            }
            isBound = loadDataServiceBinder != null;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            loadDataServiceBinder = null;
        }
    };

    // Получили уведомление об окончании загрузки данныз из сервиса. Передаем в AsyncTask для парсинга
    private BroadcastReceiver loadDataFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String cityLoadEvent = intent.getStringExtra(BroadcastEvent.CITY_LOAD_STATUS);
            if (cityLoadEvent.equals(BroadcastEvent.CITY_LOAD_STATUS_OK))
                parsingData();
        }
    };

    // Запускаем AsyncTask для парсинга данных
    private void parsingData() {
        new ParsingDataAsyncTask().execute(new CallParsingData());
    }

    // Для возврата значений из ParsingAsyncDataTask
    class CallParsingData implements ICallData {
        @Override
        public void execute(List<TheatherData> cityTheatherList, String cityName) {
            setDefaultKey(cityName);
            Observable observable = Observable.getInstance();

            if (!isCityCached(cityName)) {
                CityData cityData = new CityData(cityName, cityName,
                        cityTheatherList,
                        ResToBitmap(R.drawable.default_image),
                        ResToBitmap(R.drawable.default_image),
                        ResToBitmap(R.drawable.kazan_small),
                        "https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0");
                cityData.setFavoriteCity(false);
                cityList.add(cityData);
                observable.notify(EventsConst.addNewCity, cityData);
            } else {
                observable.notify(EventsConst.cityLoadFinish, cityTheatherList);
            }
        }

        @Override
        public String getLoadedData() {
            String data = null;
            if (loadDataServiceBinder != null)
                data = loadDataServiceBinder.getLoadedData();
            return data;
        }
    }
}
