package com.naura.cityApp.rest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {

    @GET("data/2.5/forecast")
    Single<WeatherRequest> loadWeather(@Query("q") String city,
                                       @Query("cnt") int cnt,
                                       @Query("lang") String lang,
                                       @Query("appid") String keyApi,
                                       @Query("units") String units);

    @GET("data/2.5/forecast")
    Single<WeatherRequest> loadWeather(@Query("cnt") int cnt,
                                     @Query("lang") String lang,
                                     @Query("appid") String keyApi,
                                     @Query("units") String units,
                                     @Query("lat") double latitude,
                                     @Query("lon") double longitude);


}
