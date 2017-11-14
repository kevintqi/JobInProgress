package com.sebeca.app.jobinprogress.locator;

import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationData {
    public static final String KEY_JOB_ID = "jobId";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_TIME = "dateLog";
    private static final String TAG = LocationData.class.getSimpleName();
    private final String mJobId = "5986b8180a8ea07f61558999";
    private final double mLatitude;
    private final double mLongitude;
    private final long mTime;
    private final JSONObject mJSONObject = new JSONObject();

    public LocationData(String jobId, Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        mTime = location.getTime();
        try {
            mJSONObject.put(KEY_JOB_ID, jobId);
            JSONObject locationObj = new JSONObject();
            locationObj.put(KEY_LAT, mLatitude);
            locationObj.put(KEY_LNG, mLongitude);
            mJSONObject.put(KEY_LOCATION, locationObj);
            mJSONObject.put(KEY_TIME, mTime);
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
    }

    double getLatitude() {
        return mLatitude;
    }

    double getLongitude() {
        return mLongitude;
    }

    long getTime() {
        return mTime;
    }

    JSONObject toJSON() {
        return mJSONObject;
    }
}
