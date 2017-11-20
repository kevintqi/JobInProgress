package com.sebeca.app.jobinprogress.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.sebeca.app.jobinprogress.database.AppDatabase;
import com.sebeca.app.jobinprogress.main.joblist.JobListRepository;
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
    AppDatabase providesAppDatabase(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, APP_DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    JobListRepository providesJobListRepository(Application application, AppDatabase database) {
        return new JobListRepository(application, database);
    }

    @Provides
    @Singleton
    MyRequestQueue providesMyRequestQueue(Application application) {
        return new MyRequestQueue(application);
    }
}
