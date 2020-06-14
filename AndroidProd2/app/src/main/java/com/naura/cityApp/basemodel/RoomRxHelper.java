package com.naura.cityApp.basemodel;

import com.naura.cityApp.App;
import com.naura.cityApp.database.CityDb;
import com.naura.cityApp.database.CityWeatherDb;
import com.naura.cityApp.database.basecode.CityDao;
import com.naura.cityApp.utility.CityDataToCityDbAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

// Класс для асинхронного взаимодействия с Room
public class RoomRxHelper {

    public static void callCityAll(final List<CityData> cityDataList) {
        CityDao cityDao = App.getComponent().getCityDao();
        Disposable disposable = cityDao.getAllCity()
                .map(cityDbList -> {
                    long cityCount = cityDao.getCityCount();
                    List<CityData> tempCityList = new ArrayList<>();
                    for (int i = 0; i < cityCount; i++) {
                        CityData cityData = CityDataToCityDbAdapter.convert(cityDbList.get(i));
                        List<CityWeatherDb> cityWeatherDbs = cityDao.getCityDataByName(cityDbList.get(i).cityName, 10);
                        if (cityWeatherDbs.size() > 0)
                            cityData.setWeatherDays(CityDataToCityDbAdapter.convert(cityWeatherDbs));
                        tempCityList.add(cityData);
                    }
                    return tempCityList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CityData>>() {
                    @Override
                    public void onSuccess(List<CityData> resCityDataList) {
                        cityDataList.addAll(resCityDataList);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public static void callWeatherCity(final ICallData callData, final String cityName, int recordCount) {
        CityDao cityDao = App.getComponent().getCityDao();
        Disposable disposable = cityDao.getCityWeatherWithByName(cityName, recordCount)
                .map(weatherList -> {
                    List<WeatherData> cityTheatherList = CityDataToCityDbAdapter.convert(weatherList);
                    return cityTheatherList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<WeatherData>>() {
                    @Override
                    public void onSuccess(List<WeatherData> cityTheatherList) {
                        callData.callWeatherList(cityTheatherList, cityName, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    public static void updateCity(CityDb city) {
        CityDao cityDao = App.getComponent().getCityDao();
        Completable.fromAction(() -> cityDao.updateCity(city))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    public static void callWeatherDataList(final ICallData callData, final String cityName) {
        CityDao cityDao = App.getComponent().getCityDao();
        int recordCount = 1000;
        Disposable disposable = cityDao.getCityWeatherWithByName(cityName, recordCount)
                .map(weatherList -> {
                    List<WeatherData> cityTheatherList = CityDataToCityDbAdapter.convert(weatherList);
                    return cityTheatherList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<WeatherData>>() {
                    @Override
                    public void onSuccess(List<WeatherData> cityTheatherList) {
                        // callData.execute(cityTheatherList, cityName, false);
                        callData.callAllWeatherList(cityTheatherList, cityName);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    public static long getCityID(final CityData cityData) {
        CityDao cityDao = App.getComponent().getCityDao();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future result = es.submit((Callable) () -> {
            long id = cityDao.insertCity(CityDataToCityDbAdapter.convert(cityData));
            return id;
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

    public static long insertDb(final String cityName, final CityData cityData, final List<WeatherData> cityTheatherList, final boolean cityCached) {
        CityDao cityDao = App.getComponent().getCityDao();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Long> result = es.submit(() -> {
            Long city_id;
            if (!cityCached) {
                CityDb cityDb = CityDataToCityDbAdapter.convert(cityData);
                city_id = cityDao.insertCity(cityDb);
            } else
                city_id = cityDao.getCityID(cityName);

            List<CityWeatherDb> cityWeatherDbList = CityDataToCityDbAdapter.convert(cityTheatherList, city_id);

            for (int i = 0; i < cityWeatherDbList.size(); i++) {
                Date date = cityWeatherDbList.get(i).date;
                cityDao.deleteWeatherWithDate(city_id, date);
            }
            cityDao.insertWeatherList(cityWeatherDbList);
            return city_id;
        });
        try {
            return result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
