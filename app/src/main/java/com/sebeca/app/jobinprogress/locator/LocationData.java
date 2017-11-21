package com.sebeca.app.jobinprogress.locator;

import android.location.Location;
import android.util.Log;

import com.sebeca.app.jobinprogress.database.LocationEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationData {
    public static final String KEY_JOB_ID = "jobId";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_TIME = "dateLog";

    private static final String TAG = LocationData.class.getSimpleName();
    private final JSONObject mJSONObject = new JSONObject();
    private final LocationEntity mLocationEntity;

    public LocationData(String jobId, Location location) {
        mLocationEntity = new LocationEntity();
        mLocationEntity.jobId = jobId;
        mLocationEntity.latitude = location.getLatitude();
        mLocationEntity.longitude = location.getLongitude();
        mLocationEntity.reportTime = location.getTime();
        mLocationEntity.archived = false;
        buildJSON();
    }

    public LocationData(LocationEntity locationEntity) {
        mLocationEntity = locationEntity;
        buildJSON();
    }

    double getLatitude() {
        return mLocationEntity.latitude;
    }

    double getLongitude() {
        return mLocationEntity.longitude;
    }

    long getTime() {
        return mLocationEntity.reportTime;
    }

    JSONObject toJSON() {
        return mJSONObject;
    }

    LocationEntity getLocationEntity() {
        return mLocationEntity;
    }

    private void buildJSON() {
        try {
            mJSONObject.put(KEY_JOB_ID, mLocationEntity.jobId);
            JSONObject locationObj = new JSONObject();
            locationObj.put(KEY_LAT, mLocationEntity.latitude);
            locationObj.put(KEY_LNG, mLocationEntity.longitude);
            mJSONObject.put(KEY_LOCATION, locationObj);
            mJSONObject.put(KEY_TIME, mLocationEntity.reportTime);
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
    }
}
