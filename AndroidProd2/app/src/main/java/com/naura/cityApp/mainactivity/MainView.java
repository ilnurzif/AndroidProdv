package com.naura.cityApp.mainactivity;

import android.graphics.drawable.Drawable;

interface MainView {
    void setToolBarTitle(String cityName);

    void setBackground(Drawable drawable);

    void citySearchHistoryOpen();

    void cityDetailHistoryOpen();

    void setSearchFieldVisible(Boolean visible);
}
