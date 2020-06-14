package com.naura.cityApp.di.module;

import com.naura.cityApp.rest.OpenWeather;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RetrofitModule {
    private static String baseUrl = "https://api.openweathermap.org";

    @Provides
    public String provideEndpoint() {
        return "https://api.openweathermap.org";
}

    @Provides
    @Named("keyApi")
    public String getKeyApi() {return "1d208cf3fc4085d8d8ba431d9d470fb3";};

    @Provides
    public OpenWeather initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(OpenWeather.class);
    }
}
