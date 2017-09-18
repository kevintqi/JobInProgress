package com.sebeca.app.jobinprogress.locator;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayDeque;

public class LocationUpdater implements LocationListener{
    private static final String TAG = LocationUpdater.class.getSimpleName();

    private ArrayDeque<LocationData> mItemQueue = new ArrayDeque<>();
    private final Object lock = new Object();

    public LocationData[] popItemQueue() {
        LocationData[] items;
        synchronized (lock) {
            items = (LocationData[]) mItemQueue.toArray();
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
