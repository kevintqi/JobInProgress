package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class JobMarkerEntity {
    @NonNull
    @PrimaryKey
    public String jobId = "";

    public int jobState;

    public long updateTime;

    public double latitude;

    public double longitude;
}
