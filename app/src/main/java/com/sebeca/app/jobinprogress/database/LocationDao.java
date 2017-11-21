package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocations(LocationEntity... locationEntities);

    @Query("DELETE FROM LocationEntity")
    void clearAllLocations();

    @Query("SELECT * FROM LocationEntity WHERE archived=0 ORDER BY reportTime")
    LocationEntity[] loadNewLocations();

    @Query("SELECT * FROM LocationEntity ORDER BY reportTime")
    LocationEntity[] loadAllLocations();
}
