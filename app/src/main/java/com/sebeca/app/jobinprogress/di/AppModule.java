package com.sebeca.app.jobinprogress.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.database.AppDatabase;
import com.sebeca.app.jobinprogress.locator.LocationRepository;
import com.sebeca.app.jobinprogress.main.joblist.JobListRepository;
import com.sebeca.app.jobinprogress.main.map.JobMarkerRepository;
import com.sebeca.app.jobinprogress.network.MyRequestQueue;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger App Module
 */
@Module
public class AppModule {

    private static final String APP_DATABASE_NAME = "app-database";
    private static final String APP_SHARED_PREFERENCES = "app-shared-preference";

    private Application mApp;

    AppModule(Application app) {
        mApp = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApp;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application app) {
        return app.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, APP_DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    MyRequestQueue providesMyRequestQueue(Application application) {
        return new MyRequestQueue(application);
    }

    @Provides
    @Singleton
    LocationRepository providesLocationRepository(AppDatabase database, ActiveJobDataStore activeJobDataStore) {
        return new LocationRepository(database, activeJobDataStore);
    }

    @Provides
    @Singleton
    JobListRepository providesJobListRepository(Application application, AppDatabase database, ActiveJobDataStore activeJobDataStore) {
        return new JobListRepository(application, database, activeJobDataStore);
    }

    @Provides
    @Singleton
    JobMarkerRepository providesJobMarkerRepository(AppDatabase database) {
        return new JobMarkerRepository(database);
    }

    @Provides
    @Singleton
    ActiveJobDataStore providesActiveJobDataStore(Application application) {
        return new ActiveJobDataStore(application);
    }
}
