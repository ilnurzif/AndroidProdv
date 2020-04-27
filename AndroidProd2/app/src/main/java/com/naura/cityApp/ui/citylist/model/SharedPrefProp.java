package com.naura.cityApp.ui.citylist.model;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefProp {
    SharedPreferences sharedPref;

   public  void saveDefaultCity(Context context, String cityName) {
      sharedPref = context.getSharedPreferences("citySetting",MODE_PRIVATE);
      SharedPreferences.Editor editor=sharedPref.edit();
      editor.putString("defaultCityName", cityName);
      editor.commit();
    }

    public  String loadDefaultCity(Context context) {
      sharedPref = context.getSharedPreferences("citySetting",MODE_PRIVATE);
      String defaultCityName=sharedPref.getString("defaultCityName","Москва");
      return defaultCityName;
    }
}
