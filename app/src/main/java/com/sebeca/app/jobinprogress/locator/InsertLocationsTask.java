package com.sebeca.app.jobinprogress.locator;

import com.sebeca.app.jobinprogress.database.LocationDao;

import java.util.ArrayList;

class InsertLocationsTask implements Runnable {
    private final LocationDao mLocationDao;
    private final ArrayList<LocationData> mLocationData;

    InsertLocationsTask(LocationDao locationDao,
                        ArrayList<LocationData> locationData) {
        mLocationDao = locationDao;
        mLocationData = locationData;
    }

    @Override
    public void run() {
        for (LocationData item : mLocationData) {
            mLocationDao.insertLocations(item.getLocationEntity());
        }
    }
}
