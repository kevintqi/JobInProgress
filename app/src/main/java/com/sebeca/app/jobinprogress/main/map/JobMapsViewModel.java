package com.sebeca.app.jobinprogress.main.map;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.locator.LocationData;
import com.sebeca.app.jobinprogress.locator.LocationRepository;

import java.util.ArrayList;

import javax.inject.Inject;


public class JobMapsViewModel extends AndroidViewModel implements LocationRepository.LoadLocationsCallback {
    @Inject
    LocationRepository mLocationRepository;

    @Inject
    JobMarkerRepository mJobMarkerRepository;

    private final MutableLiveData<ArrayList<LocationData>> mLocationData = new MutableLiveData<>();

    public JobMapsViewModel(@NonNull Application app) {
        super(app);
        ((App) app).getAppComponent().inject(this);
        mLocationRepository.requestAllLocations(this);
    }

    LiveData<ArrayList<LocationData>> getLocationData() {
        return mLocationData;
    }

    LiveData<ArrayList<LocationData>> getNewLocations() {
        return mLocationRepository.getNewLocations();
    }

    LiveData<ArrayList<JobMarker>> getJobMakers() {
        return mJobMarkerRepository.getJobMakers();
    }

    @Override
    public void onLocationsReady(ArrayList<LocationData> locations) {
        mLocationData.postValue(locations);
    }
}
