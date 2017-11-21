package com.sebeca.app.jobinprogress.locator;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.database.AppDatabase;
import com.sebeca.app.jobinprogress.database.DataTaskRunner;
import com.sebeca.app.jobinprogress.database.LocationDao;
import com.sebeca.app.jobinprogress.database.LocationEntity;

import java.util.ArrayList;

/**
 * Data Repository handles all location interactions
 */

public class LocationRepository extends DataTaskRunner {
    private static final String TAG = LocationRepository.class.getSimpleName();

    private final ActiveJobDataStore mActiveJobDataStore;
    private final LocationDao mLocationDao;
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            String activeJobId = mActiveJobDataStore.get();
            Log.i(TAG, "activeJobId = " + activeJobId);
            if (activeJobId != null) {
                if (locationResult != null) {
                    ArrayList<LocationData> locationData = new ArrayList<>();
                    Log.i(TAG, " LocationResult = " + locationResult.toString());
                    for (Location item : locationResult.getLocations()) {
                        LocationData data = new LocationData(activeJobId, item);
                        locationData.add(data);
                    }
                    run(new InsertLocationsTask(locationData));
                }
            }
        }
    };
    private LoadLocationsCallback mLoadLocationsCallback;

    public LocationRepository(AppDatabase database, ActiveJobDataStore activeJobDataStore) {
        super(TAG);
        mLocationDao = database.locationDao();
        mActiveJobDataStore = activeJobDataStore;
    }

    public LocationCallback getLocationCallback() {
        return mLocationCallback;
    }

    void requestLocations(LoadLocationsCallback loadLocationsCallback) {
        mLoadLocationsCallback = loadLocationsCallback;
        run(new LoadLocationsTask());
    }

    void archiveLocations(ArrayList<LocationData> locations) {
        run(new ArchiveLocationsTask(locations));
    }

    interface LoadLocationsCallback {
        void onLocationsReady(ArrayList<LocationData> locations);
    }

    private class InsertLocationsTask implements Runnable {
        private final ArrayList<LocationData> mLocationData;

        InsertLocationsTask(ArrayList<LocationData> locationData) {
            mLocationData = locationData;
        }

        @Override
        public void run() {
            for (LocationData item : mLocationData) {
                mLocationDao.insertLocations(item.getLocationEntity());
            }
        }
    }

    private class LoadLocationsTask implements Runnable {

        @Override
        public void run() {
            ArrayList<LocationData> locations = new ArrayList<>();
            LocationEntity[] locationEntities = mLocationDao.loadNewLocations();
            for (LocationEntity item : locationEntities) {
                LocationData data = new LocationData(item);
                locations.add(data);
            }
            mLoadLocationsCallback.onLocationsReady(locations);
        }
    }

    private class ArchiveLocationsTask implements Runnable {
        private final ArrayList<LocationData> mLocations;

        ArchiveLocationsTask(ArrayList<LocationData> locations) {
            mLocations = locations;
        }

        @Override
        public void run() {
            LocationEntity locationEntities[] = new LocationEntity[mLocations.size()];
            int i = 0;
            for (LocationData item : mLocations) {
                LocationEntity entity = item.getLocationEntity();
                entity.archived = true;
                locationEntities[i++] = entity;
            }
            mLocationDao.insertLocations(locationEntities);
        }
    }
}
