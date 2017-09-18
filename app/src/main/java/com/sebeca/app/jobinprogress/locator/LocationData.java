package com.sebeca.app.jobinprogress.locator;

import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationData {
    private static final String TAG = LocationData.class.getSimpleName();
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_TIME = "time";
    private final double mLatitude;
    private final double mAltitude;
    private final long mTime;
    private final JSONObject mJSONObject = new JSONObject();

    public LocationData(Location location) {
        mLatitude = location.getLatitude();
        mAltitude = location.getAltitude();
        mTime = location.getTime();
        try {
            mJSONObject.put(KEY_LAT, mLatitude);
            mJSONObject.put(KEY_LNG, mAltitude);
            mJSONObject.put(KEY_TIME, mTime);
        } catch (JSONException e) {
            Log.e(TAG, "", e);
            e.printStackTrace();
        }
    }

    double getLatitude() {
        return mLatitude;
    }

    double getAltitude() {
            return mAltitude;
    }

    long getTime() {
        return mTime;
    }

    JSONObject toJSON() {
        return mJSONObject;
    }
}
