package com.sebeca.app.jobinprogress.locator;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.database.AppDatabase;
import com.sebeca.app.jobinprogress.database.DataTaskRunner;
import com.sebeca.app.jobinprogress.database.LocationDao;

import java.util.ArrayList;

/**
 * Data Repository handles all location interactions
 */

public class LocationRepository extends DataTaskRunner {
    public interface LoadLocationsCallback {
        void onLocationsReady(ArrayList<LocationData> locations);
    }

    private static final String TAG = LocationRepository.class.getSimpleName();

    private final ActiveJobDataStore mActiveJobDataStore;
    private final LocationDao mLocationDao;
    private Location mLastLocation;
    private final MutableLiveData<ArrayList<LocationData>> mNewLocationData = new MutableLiveData<>();

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null) {
                mLastLocation = locationResult.getLastLocation();

                String activeJobId = mActiveJobDataStore.get();
                Log.i(TAG, "activeJobId = " + activeJobId);
                if (activeJobId != null) {
                    mLastLocation = locationResult.getLastLocation();
                    ArrayList<LocationData> locationData = new ArrayList<>();
                    Log.i(TAG, " LocationResult = " + locationResult.toString());
                    for (Location item : locationResult.getLocations()) {
                        LocationData data = new LocationData(activeJobId, item);
                        locationData.add(data);
                    }
                    mNewLocationData.postValue(locationData);
                    run(new InsertLocationsTask(mLocationDao, locationData));
                }
            }
        }
    };

    public LocationRepository(AppDatabase database, ActiveJobDataStore activeJobDataStore) {
        super(TAG);
        mLocationDao = database.locationDao();
        mActiveJobDataStore = activeJobDataStore;
    }

    public LocationCallback getLocationCallback() {
        return mLocationCallback;
    }

    public Location getLastLocation() {
        return mLastLocation;
    }

    public LiveData<ArrayList<LocationData>> getNewLocations() {
        return mNewLocationData;
    }

    public void requestAllLocations(LoadLocationsCallback callback) {
        run(new LoadAllLocationsTask(mLocationDao, callback));
    }

    public void requestNewLocations(LoadLocationsCallback callback) {
        run(new LoadNewLocationsTask(mLocationDao, callback));
    }

    void archiveLocations(ArrayList<LocationData> locations) {
        run(new ArchiveLocationsTask(mLocationDao, locations));
    }
}
