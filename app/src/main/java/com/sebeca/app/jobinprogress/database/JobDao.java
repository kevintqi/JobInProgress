package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface JobDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertJobs(JobEntity... jobs);

    @Update
    void updateJobs(JobEntity... jobs);

    @Delete
    void deleteJobs(JobEntity... jobs);

    @Query("DELETE FROM JobEntity")
    void clearAllJobs();

    @Query("SELECT * FROM JobEntity ORDER BY priority")
    JobEntity[] loadAllJobs();
}
