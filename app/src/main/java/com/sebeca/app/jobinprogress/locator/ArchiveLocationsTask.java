package com.sebeca.app.jobinprogress.locator;

import com.sebeca.app.jobinprogress.database.LocationDao;
import com.sebeca.app.jobinprogress.database.LocationEntity;

import java.util.ArrayList;


class ArchiveLocationsTask implements Runnable {
    private final LocationDao mLocationDao;
    private final ArrayList<LocationData> mLocations;

    ArchiveLocationsTask(LocationDao locationDao, ArrayList<LocationData> locations) {
        mLocationDao = locationDao;
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
        mLocationDao.updateLocations(locationEntities);
    }
}
