package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.sebeca.app.jobinprogress.main.joblist.Job;

/**
 * Created by kevinqi on 11/18/17.
 */

@Entity
public class JobEntity {
    @PrimaryKey
    public @NonNull
    String id;
    public int priority;
    public String address;
    public String targetStartTime;
    public String targetEndTime;
    public long actualStartTime;
    public long actualEndTime;
    public int status = Job.NEW;
    public boolean active = false;
}
