package com.naura.cityApp.utility;

import android.content.SharedPreferences;
import com.naura.cityApp.App;
import javax.inject.Inject;


public class SharedPrefProp {

    @Inject
    SharedPreferences sharedPref;

    @Inject
    SharedPreferences.Editor editor;

    public SharedPrefProp() {
        App.getComponent().inject(this);
    }

    public  void saveDefaultCity(String cityName) {
      editor.putString("defaultCityName", cityName);
      editor.commit();
    }

    public  String loadDefaultCity() {
      String defaultCityName=sharedPref.getString("defaultCityName","Москва");
      return defaultCityName;
    }
}
