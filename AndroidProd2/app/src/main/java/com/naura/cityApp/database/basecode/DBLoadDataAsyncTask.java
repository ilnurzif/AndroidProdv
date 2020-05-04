package com.naura.cityApp.database.basecode;
import android.os.AsyncTask;

import com.naura.cityApp.App;
import com.naura.cityApp.ui.theatherdata.CityDataToCityDbAdapter;
import com.naura.cityApp.cityloader.CityLoader;
import com.naura.cityApp.cityloader.ICallData;
import com.naura.cityApp.database.basecode.dao.CityDao;
import com.naura.cityApp.cityloader.database.CityWeatherDb;
import com.naura.cityApp.ui.theatherdata.TheatherData;

import java.util.List;

public class DBLoadDataAsyncTask extends AsyncTask<ICallData, Void, Void> {
    private static List<TheatherData> cityTheatherList;
    private CityDao cityDao;
    private List<CityWeatherDb> cityWeatherDbList;
    private ICallData callData;
    private CityLoader cityLoader;
    private String cityName;

    public DBLoadDataAsyncTask(CityLoader cityLoader) {
        this.cityLoader = cityLoader;
    }

    private void request(String cityName) {
        cityDao = App.getInstance().getCityDao();
        int recordCount = 3;  // за сколько дней нужно вернуть сохраненные данные
        cityWeatherDbList = cityDao.getCityWeatherWithByName(cityName, recordCount);
        if (cityWeatherDbList.size() == 0) return;
        cityTheatherList = CityDataToCityDbAdapter.convert(cityWeatherDbList);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        cityName=cityLoader.getDefaultCityName();
    }

    @Override
    protected Void doInBackground(ICallData... iCallData) {
        callData = iCallData[0];
        request(cityName);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (cityTheatherList==null)
            return;
        callData.execute(cityTheatherList, cityName, false);
    }
}
