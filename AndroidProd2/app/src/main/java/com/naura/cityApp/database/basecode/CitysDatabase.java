package com.naura.cityApp.database.basecode;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.naura.cityApp.database.CityDb;
import com.naura.cityApp.database.CityWeatherDb;

@Database(entities = {CityDb.class, CityWeatherDb.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class CitysDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}

