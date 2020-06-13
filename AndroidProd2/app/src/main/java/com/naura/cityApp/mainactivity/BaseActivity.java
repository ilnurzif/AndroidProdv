package com.naura.cityApp.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.naura.cityApp.observercode.Observer;
import com.naura.myapplication.R;


public class BaseActivity extends AppCompatActivity implements Observer {
    private static final String NameSharedPreference = "LOGIN";
    private static final String IsDarkTheme = "IS_DARK_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
       setThemeD();
    }

    protected void setThemeD() {
       if (isDarkTheme()) {
            setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        } else {
             setTheme(R.style.AppTheme_NoActionBar);
        }
    }

    protected boolean isDarkTheme() {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getBoolean(IsDarkTheme, true);
    }

    public void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IsDarkTheme, isDarkTheme);
        editor.apply();
    }

    @Override
    public <T> void update(String eventName, T val) {

    }
}

