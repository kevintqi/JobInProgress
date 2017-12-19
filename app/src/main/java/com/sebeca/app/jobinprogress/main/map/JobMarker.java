package com.sebeca.app.jobinprogress.main.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.sebeca.app.jobinprogress.database.JobMarkerEntity;


public class JobMarker {
    private final JobMarkerEntity mJobMarkerEntity;
    private final LatLng mLocation;

    public JobMarker(String jobId, String jobState, Location location) {
        mJobMarkerEntity = new JobMarkerEntity();
        mJobMarkerEntity.jobId = jobId;
        mJobMarkerEntity.jobState = jobState;
        mJobMarkerEntity.latitude = location.getLatitude();
        mJobMarkerEntity.longitude = location.getLongitude();
        mLocation = new LatLng(mJobMarkerEntity.latitude, mJobMarkerEntity.longitude);
        mJobMarkerEntity.updateTime = System.currentTimeMillis();
    }

    public JobMarker(JobMarkerEntity jobMarkerEntity) {
        mJobMarkerEntity = jobMarkerEntity;
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
