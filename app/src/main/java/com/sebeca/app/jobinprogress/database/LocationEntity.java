package com.sebeca.app.jobinprogress.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LocationEntity {
    @PrimaryKey
    public long reportTime;

    public double latitude;

    public double longitude;

    public String jobId;

    public boolean archived = false;
}
