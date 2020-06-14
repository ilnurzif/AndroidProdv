package com.naura.cityApp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(indices = {@Index(value = {"city_id"})}, foreignKeys = @ForeignKey(entity = CityDb.class,
        parentColumns = "id",
        childColumns = "city_id", onDelete = CASCADE))

public class CityWeatherDb {
    public static final String MDATE = "MDate";
    public static final String TEMPERATURE = "Temperature";
    public static final String HUMIDITY = "Humidity";
    public static final String PRESSURE = "Pressure";
    public static final String CITY_ID = "city_id";
    public static final String DESCRIPTION = "description";
    public static final String ICON_URL = "icon";

    public CityWeatherDb(long cityId, Date date, float tempereture, float humitity, float pressure, String description, String iconurl) {
        this.date = date;
        this.tempereture = tempereture;
        this.humitity = humitity;
        this.pressure = pressure;
        this.cityId = cityId;
        this.description = description;
        this.iconurl = iconurl;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public long ID;

    @ColumnInfo(name = MDATE)
    public Date date;

    @ColumnInfo(name = TEMPERATURE)
    public float tempereture;

    @ColumnInfo(name = HUMIDITY)
    public float humitity;

    @ColumnInfo(name = PRESSURE)
    public float pressure;

    @ColumnInfo(name = CITY_ID)
    public long cityId;

    @ColumnInfo(name = DESCRIPTION)
    public String description;

    @ColumnInfo(name = ICON_URL)
    public String iconurl;
}
