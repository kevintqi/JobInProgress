package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class JobMarkerEntity {
    @PrimaryKey
    public long updateTime;

    public double latitude;

    public double longitude;

    public String jobId;

    public String jobState;
}
