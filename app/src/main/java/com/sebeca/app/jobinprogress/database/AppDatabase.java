package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {JobEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract JobDao jobDao();
}