package com.naura.cityApp.database.basecode;

import com.naura.cityApp.database.basecode.dao.CityDao;

public class CitySource {
    private CityDao cityDao;

    public CitySource(CityDao cityDao) {
        this.cityDao = cityDao;
    }
}
