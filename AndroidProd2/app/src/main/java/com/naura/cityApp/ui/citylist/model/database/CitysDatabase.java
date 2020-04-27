package com.naura.cityApp.ui.citylist.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.naura.cityApp.database.basecode.DateConverter;
import com.naura.cityApp.ui.citylist.model.database.dao.CityDao;

@Database(entities = {CityDb.class, CityWeatherDb.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class CitysDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}

