package com.sebeca.app.jobinprogress.main.map;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.locator.LocationData;
import com.sebeca.app.jobinprogress.locator.LocationRepository;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by kevinqi on 12/2/17.
 */

public class JobMapsViewModel extends AndroidViewModel implements LocationRepository.LoadJobLocationsCallback {

    private final MutableLiveData<ArrayList<LocationData>> mLocationData = new MutableLiveData<>();
    @Inject
    LocationRepository mLocationRepository;
    @Inject
    JobMarkerRepository mJobMarkerRepository;
    @Inject
    ActiveJobDataStore mActiveJobDataStore;

    public JobMapsViewModel(@NonNull Application app) {
        super(app);
        ((App) app).getAppComponent().inject(this);
        mLocationRepository.requestActiveJobLocations(this);
    }

    public LiveData<ArrayList<LocationData>> getLocationData() {
        return mLocationData;
    }

    @Override
    public void onLocationsReady(ArrayList<LocationData> locations) {
        mLocationData.postValue(locations);
    }

    public LiveData<ArrayList<JobMarker>> getJobMakers() {
        return mJobMarkerRepository.getJobMakers(mActiveJobDataStore.get());
    }
}
