package com.sebeca.app.jobinprogress.main.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.sebeca.app.jobinprogress.database.JobMarkerEntity;

/**
 * Created by kevinqi on 12/16/17.
 */

public class JobMarker {
    private JobMarkerEntity mJobMarkerEntity;
    private LatLng mLocation;

    public JobMarker(String jobId, String jobState) {
        mJobMarkerEntity = new JobMarkerEntity();
        mJobMarkerEntity.jobId = jobId;
        mJobMarkerEntity.jobState = jobState;
        mJobMarkerEntity.updateTime = System.currentTimeMillis();
    }

    public JobMarker(JobMarkerEntity jobMarkerEntity) {
        mJobMarkerEntity = jobMarkerEntity;
        mLocation = new LatLng(mJobMarkerEntity.latitude, mJobMarkerEntity.longitude);
    }

    public void setLocation(Location location) {
        mJobMarkerEntity.latitude = location.getLatitude();
        mJobMarkerEntity.longitude = location.getLongitude();
        mLocation = new LatLng(mJobMarkerEntity.latitude, mJobMarkerEntity.longitude);
    }

    public JobMarkerEntity getJobMarkerEntity() {
        return mJobMarkerEntity;
    }

    public String getJobId() {
        return mJobMarkerEntity.jobId;
    }

    public String getJobState() {
        return mJobMarkerEntity.jobState;
    }

    public LatLng getJobLocation() {
        return mLocation;
    }
}
