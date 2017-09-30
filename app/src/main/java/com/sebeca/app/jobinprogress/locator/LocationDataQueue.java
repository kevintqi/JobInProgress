package com.sebeca.app.jobinprogress.locator;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayDeque;

public class LocationDataQueue implements LocationListener {
    private static final String TAG = LocationDataQueue.class.getSimpleName();
    private final Object lock = new Object();
    private ArrayDeque<LocationData> mItemQueue = new ArrayDeque<>();

    public Object[] popDataQueue() {
        Object[] items;
        synchronized (lock) {
            items = mItemQueue.toArray();
            mItemQueue.clear();
        }
        return items;
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationData e = new LocationData(location);
        synchronized (lock) {
            mItemQueue.push(e);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
        Log.e(TAG, "OnStatusChanged: status = " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e(TAG, "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e(TAG, "onProviderDisabled");
    }
}
