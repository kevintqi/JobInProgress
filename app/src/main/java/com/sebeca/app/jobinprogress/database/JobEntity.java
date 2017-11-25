package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.sebeca.app.jobinprogress.main.joblist.Job;

@Entity
public class JobEntity {
    @PrimaryKey
    @NonNull
    public String id;

    public int priority;

    public String address;

    public long startTime = 0L;

    public int totalDuration = 0;

    public int totalBlockedDuration = 0;

    public int status = Job.NEW;

    public long lastUpdateTime = 0L;
}
