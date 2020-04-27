package com.naura.cityApp.ui.citylist.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {CityDb.CITY_NAME})})
public class CityDb {

    public CityDb(String cityKey, String cityName, String infoUrl, String imageUrl) {
        this.cityKey = cityKey;
        this.cityName = cityName;
        this.infoUrl = infoUrl;
        this.imageUrl = imageUrl;
    }

    public final static String ID = "id";
    public final static String CITY_NAME = "city_name";
    public final static String CITY_KEY = "city_key";
    public final static String INFO_URL = "info_key";
    public final static String IMAGE_URL = "image_url";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = CITY_KEY)
    public String cityKey;

    @ColumnInfo(name = CITY_NAME)
    public String cityName;

    @ColumnInfo(name = INFO_URL)
    public String infoUrl;

    @ColumnInfo(name = IMAGE_URL)
    public String imageUrl;
}
