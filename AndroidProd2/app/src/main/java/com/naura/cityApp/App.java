package com.naura.cityApp;

import android.app.Application;

import androidx.room.Room;

import com.naura.cityApp.database.basecode.CitysDatabase;
import com.naura.cityApp.database.basecode.dao.CityDao;


public class App extends Application {
    private static App singleApp;
    private CitysDatabase db;

    public static App getInstance() {
        return singleApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleApp = this;

        db=Room.databaseBuilder(
                getApplicationContext(),
                CitysDatabase.class,
                "cityweather")
              //  .allowMainThreadQueries() //для отладки
                .build();
    }

    public CityDao getCityDao() {
        return db.getCityDao();
    }
}
