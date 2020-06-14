package com.naura.cityApp.di.module;

import android.content.Context;

import androidx.room.Room;

import com.naura.cityApp.database.CityDb;
import com.naura.cityApp.database.basecode.CitysDatabase;
import com.naura.cityApp.database.basecode.CityDao;
import com.naura.cityApp.di.ApplicationContext;
import com.naura.cityApp.di.DatabaseInfo;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;


@Module
public class DatabaseModule {
    @ApplicationContext
    private final Context mContext;

   @DatabaseInfo
    private final String mDBName = "cityweather18";

    public DatabaseModule (@ApplicationContext Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    CitysDatabase provideDatabase () {
        return Room.databaseBuilder(
                mContext,
                CitysDatabase.class,
                mDBName)
                .allowMainThreadQueries()
                .build();
      }

   @Provides
   Single<List<CityDb>> getUsers(CityDao cityDao) {
       return cityDao.getAllCity();
    }


    @Singleton
    @Provides
    CityDao provideUsersDao(CitysDatabase db) { return db.getCityDao(); }

    @Provides
    @Named("citycount")
    public long getCityCount(CityDao cityDao ) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        try {
        Future result = es.submit((Callable) () -> {
            long cityCount = cityDao.getCityCount();
            return cityCount;
        });
            return (long) result.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
