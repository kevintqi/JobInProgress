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

    public long startTime;

    public int duration;

    public int status = Job.NEW;
}
