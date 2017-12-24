package com.sebeca.app.jobinprogress.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface JobMarkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJobMarks(JobMarkerEntity... jobMarkerEntities);

    @Query("DELETE FROM JobMarkerEntity")
    void clearJobMarkers();

    @Query("SELECT * FROM JobMarkerEntity ORDER BY jobId")
    JobMarkerEntity[] loadJobMarkers();
}
