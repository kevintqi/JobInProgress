package com.sebeca.app.jobinprogress.locator;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.data.LocationDataStore;

import org.json.JSONArray;

/**
 * Callback when location result becomes available
 */

public class LocationUpdaterCallback extends LocationCallback {
    private static final String TAG = LocationUpdaterCallback.class.getSimpleName();

    private Context mContext;
    private ActiveJobDataStore mActiveJobDataStore;
    private LocationDataStore mLocationDataStore;

    public LocationUpdaterCallback(Context context) {
        mActiveJobDataStore = ActiveJobDataStore.getInstance(context);
        mLocationDataStore = LocationDataStore.getInstance(context);
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);

        String activeJobId = mActiveJobDataStore.get();
        Log.i(TAG, "activeJobId = " + activeJobId);
        if (activeJobId != null) {
            if (locationResult != null) {
                Log.i(TAG, " LocationResult = " + locationResult.toString());
                JSONArray locations = mLocationDataStore.get();
                if (locations == null) {
                    locations = new JSONArray();
                }
                for (Location item : locationResult.getLocations()) {
                    LocationData data = new LocationData(activeJobId, item);
                    locations.put(data.toJSON());
                    Log.i(TAG, data.toJSON().toString());
                }
                mLocationDataStore.put(locations);
            }
        }
    }
}
