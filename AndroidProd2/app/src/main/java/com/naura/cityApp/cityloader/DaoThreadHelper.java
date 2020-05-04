package com.naura.cityApp.cityloader;

import com.naura.cityApp.App;
import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.cityloader.database.CityDb;
import com.naura.cityApp.cityloader.database.CityWeatherDb;
import com.naura.cityApp.database.basecode.dao.CityDao;
import com.naura.cityApp.ui.theatherdata.CityDataToCityDbAdapter;
import com.naura.cityApp.ui.theatherdata.TheatherData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Класс для асинхронного взаимодействия с Room
public class DaoThreadHelper {
    public static List<CityData> getCityAll() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future result = es.submit(new Callable() {
            @Override
            public List<CityData> call() throws Exception {
                CityDao cityDao = App.getInstance().getCityDao();
                long cityCount = cityDao.getCityCount();
                List<CityDb> cityDbList = cityDao.getAll();
                List<CityData> tempCityList=new ArrayList<>();
                for (int i = 0; i < cityCount; i++) {
                    CityData cityData = CityDataToCityDbAdapter.convert(cityDbList.get(i));
                    tempCityList.add(cityData);
                }
                return tempCityList;
            }
        });
        try {
            return (List<CityData>) result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getCityID(final CityData cityData) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future result = es.submit(new Callable() {
            @Override
            public Long call() throws Exception {
                CityDao cityDao = App.getInstance().getCityDao();
                long id = cityDao.insertCity(CityDataToCityDbAdapter.convert(cityData));
                return id;
            }
        });
        try {
            return (long) result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long getCityCount() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future result = es.submit(new Callable() {
            @Override
            public Long call() throws Exception {
                CityDao cityDao = App.getInstance().getCityDao();
                long cityCount = cityDao.getCityCount();
                return cityCount;
            }
        });
        try {
            return (long) result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int insertDb(final String cityName, final CityData cityData, final List<TheatherData> cityTheatherList, final boolean cityCached) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future result = es.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                long city_id;
                CityDao cityDao = App.getInstance().getCityDao();
                 if (!cityCached) {
                    CityDb cityDb = CityDataToCityDbAdapter.convert(cityData);
                    city_id = cityDao.insertCity(cityDb);
                } else
                    city_id = cityDao.getCityID(cityName);

                List<CityWeatherDb> cityWeatherDbList = CityDataToCityDbAdapter.convert(cityTheatherList, city_id);

                for (int i = 0; i < cityWeatherDbList.size(); i++) {
                    Date date=cityWeatherDbList.get(i).date;
                    cityDao.deleteWeatherWithDate(city_id,date);
                }
                cityDao.insertWeatherList(cityWeatherDbList);
                return 0; // для отслеживания успешности вставки
            }
        });
        try {
            return (Integer) result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
