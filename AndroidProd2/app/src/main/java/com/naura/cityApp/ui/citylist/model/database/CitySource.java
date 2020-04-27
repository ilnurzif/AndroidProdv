package com.naura.cityApp.ui.citylist.model.database;

import com.naura.cityApp.ui.citylist.model.database.dao.CityDao;

public class CitySource {
    private CityDao cityDao;

    public CitySource(CityDao cityDao) {
        this.cityDao = cityDao;
    }
}
