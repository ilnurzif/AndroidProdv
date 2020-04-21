package com.naura.cityApp.ui.citylist;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.naura.cityApp.observercode.BroadcastEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoadDataService extends Service {
    private static final String OPEN_WEATHER_API_KEY = "1d208cf3fc4085d8d8ba431d9d470fb3";
    private static final String OPEN_WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&cnt=12&lang=ru";
    private static final String KEY = "x-api-key";
    private final IBinder loadDataServiceBinder = new LoadDataServiceBinder();
    private String loadedData;
    private Intent intent;

    public LoadDataService() {
    }

    @Override
    public void onCreate(){

      super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    private String getData(String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_API_URL, city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }
            reader.close();
                return rawData.toString();
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private void sendBroadcastEvent(String loadStatus) {
        Intent broadcastIntent = new Intent(BroadcastEvent.BROADCAST_ACTION_LOADCITYFINISHED);
        broadcastIntent.putExtra(BroadcastEvent.CITY_LOAD_STATUS, loadStatus);
        getApplicationContext().sendBroadcast(broadcastIntent);
    }

    public String getLoadedData() {
       return loadedData;
    }

    private void loadCity(final String cityName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadedData = getData(cityName);
                if (loadedData==null)
                    sendBroadcastEvent(BroadcastEvent.CITY_LOAD_STATUS_ERROR);
                else
                    sendBroadcastEvent(BroadcastEvent.CITY_LOAD_STATUS_OK);
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        this.intent=intent;
        return loadDataServiceBinder;
    }


    public class LoadDataServiceBinder extends Binder {
        LoadDataService getService() {
            return LoadDataService.this;
        }
        public String getLoadedData() {return getService().getLoadedData();}
        public void startLoaddata(String cityName) {
            loadCity(cityName);
        }
    }
}
