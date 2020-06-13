package com.naura.cityApp.di.module;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RetrofitModule {
    @Provides
    public String provideEndpoint() {
        return "https://api.openweathermap.org";
}

/*
    @Provides
    public Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder().
                baseUrl(baseUrl).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
*/


/*    @Provides
    @Named("RetrofitUsers")
    public Single<List<GitHubUser>> getRequest(GitHubUsersApi api){
     return api.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Provides
    public Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder().
                baseUrl(baseUrl).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }

        @Provides
    public GitHubUsersApi getApi(Retrofit retrofit) {
        GitHubUsersApi gitHubUsersApi=retrofit.create(GitHubUsersApi.class);
        return gitHubUsersApi;
    }*/
}
