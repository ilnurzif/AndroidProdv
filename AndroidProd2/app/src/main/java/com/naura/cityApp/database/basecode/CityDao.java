package com.naura.cityApp.database.basecode;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.naura.cityApp.database.CityDb;
import com.naura.cityApp.database.CityWeatherDb;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCity(CityDb city);

    @Update
    void updateCity(CityDb city);

    @Delete
    void deleteCity(CityDb city);

    @Query("Delete FROM citydb")
    int clearCitys();

    @Query("SELECT COUNT() FROM citydb")
    long getCityCount();

    @Query("Select * FROM citydb")
    List<CityDb> getAll();

    @Query("Select * FROM citydb")
    Single<List<CityDb>> getAllCity();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(CityWeatherDb cityWeatherDb);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherList(List<CityWeatherDb> weatherList);

    @Query("Select id FROM citydb where city_name=:cityname")
    long getCityID(String cityname);

    @Query("Select * from cityweatherdb left join citydb on cityweatherdb.city_id=citydb.id where citydb.city_name=:cityname order by mdate limit :limit")
    Single<List<CityWeatherDb>> getCityWeatherWithByName(String cityname, int limit);

    @Query("Select * from cityweatherdb left join citydb on cityweatherdb.city_id=citydb.id where citydb.city_name=:cityname order by mdate limit :limit")
    List<CityWeatherDb> getCityDataByName(String cityname, int limit);

    @Query("Select COUNT() from cityweatherdb")
    long getWeatherCount();

    @Query("DELETE FROM cityweatherdb WHERE city_id=:cityid and mdate=:date")
    void deleteWeatherWithDate(long cityid, Date date);
}
