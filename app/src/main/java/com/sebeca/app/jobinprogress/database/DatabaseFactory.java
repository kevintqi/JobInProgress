package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by kevinqi on 11/18/17.
 */

public class DatabaseFactory {
    private static final String APP_DATABASE_NAME = "app-database";
    private static AppDatabase mAppDatabase;

    public static AppDatabase get(Context context) {
        if (mAppDatabase == null) {
            mAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, APP_DATABASE_NAME).build();
        }
        return mAppDatabase;
    }
}
