package com.naura.cityApp.ui.citydetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.naura.cityApp.ui.theatherdata.TheatherData;

import java.util.List;

public class CityData {
    private String key;
    private String name;
    private List<TheatherData> theatherDays;
    private Bitmap verticalImage;
    private Bitmap horisontalImage;
    private Bitmap smallImage;
    private String infoUrl;
    private boolean favoriteCity = true;

    public boolean isFavoriteCity() {
        return favoriteCity;
    }

    public void setFavoriteCity(boolean favoriteCity) {
        this.favoriteCity = favoriteCity;
    }

    public CityData(String key,
                    String name,
                    List<TheatherData> theatherDays,
                    Bitmap verticalImage,
                    Bitmap horisontalImage,
                    Bitmap smallImage,
                    String infoUrl) {

        this.key = key;
        this.name = name;
        this.verticalImage = verticalImage;
        this.horisontalImage = horisontalImage;
        this.theatherDays = theatherDays;
        this.smallImage = smallImage;
        this.infoUrl = infoUrl;
    }

    public String getKey() {
        return key;
    }

    public Bitmap getSmallImage() {
        return smallImage;
    }

    public String getTemperatureNow() {
        String st = theatherDays.get(0).getTemperature();
        return st;
    }

    public String getAirhumidityNow() {
        String st = theatherDays.get(0).getAirhumidity();
        return st;
    }

    public String getName() {
        return name;
    }

    public List<TheatherData> getTheatherDays() {
        return theatherDays;
    }

    public Bitmap getVerticalImage() {
        return verticalImage;
    }

    public Bitmap getHorisontalImage() {
        return horisontalImage;
    }

    public void openInfo(Context context) {
        Uri uri = Uri.parse(infoUrl);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(browserIntent);
    }
}
