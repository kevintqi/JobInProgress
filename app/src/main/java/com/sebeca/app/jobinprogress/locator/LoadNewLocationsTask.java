package com.sebeca.app.jobinprogress.locator;


import com.sebeca.app.jobinprogress.database.LocationDao;
import com.sebeca.app.jobinprogress.database.LocationEntity;

import java.util.ArrayList;

class LoadNewLocationsTask implements Runnable {
    private final LocationDao mLocationDao;
    private final LocationRepository.LoadLocationsCallback mCallback;

    LoadNewLocationsTask(LocationDao locationDao, LocationRepository.LoadLocationsCallback callback) {
        mLocationDao = locationDao;
        mCallback = callback;
    }

    @Override
    public void run() {
        if (mCallback != null) {
            ArrayList<LocationData> locations = new ArrayList<>();
            LocationEntity[] locationEntities = mLocationDao.loadNewLocations();
            for (LocationEntity item : locationEntities) {
                LocationData data = new LocationData(item);
                locations.add(data);
            }
            mCallback.onLocationsReady(locations);
        }
    }
}
