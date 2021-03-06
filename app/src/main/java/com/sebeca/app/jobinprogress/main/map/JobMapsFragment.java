package com.sebeca.app.jobinprogress.main.map;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.locator.LocationData;
import com.sebeca.app.jobinprogress.main.joblist.Job;

import java.util.ArrayList;

/**
 * Map View of Jobs
 */
public class JobMapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = JobMapsFragment.class.getSimpleName();
    private static final double DEFAULT_ZOOM = 15.0;
    private static final int POLYLINE_WIDTH = 5;
    private GoogleMap mMap;
    private ArrayList<LocationData> mLocationData;
    private ArrayList<JobMarker> mJobMarkers;
    private final ArrayList<Marker> mMarker = new ArrayList<>();
    private final ArrayList<Integer> mColors = new ArrayList<>();
    private LatLng mLastLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mColors.add(Color.BLUE);
        mColors.add(Color.MAGENTA);
        mColors.add(Color.CYAN);
        JobMapsViewModel mJobMapsViewModel = ViewModelProviders.of(this).get(JobMapsViewModel.class);
        final Observer<ArrayList<LocationData>> locationDataObserver = new Observer<ArrayList<LocationData>>() {
            @Override
            public void onChanged(@Nullable ArrayList<LocationData> locationData) {
                mLocationData = locationData;
                if (mMap != null) {
                    drawPolyline(mLocationData, false);
                }
            }
        };
        mJobMapsViewModel.getLocationData().observe(this, locationDataObserver);
        final Observer<ArrayList<LocationData>> newLocationObserver = new Observer<ArrayList<LocationData>>() {
            @Override
            public void onChanged(@Nullable ArrayList<LocationData> locationData) {
                if (mMap != null) {
                    drawPolyline(locationData, true);
                }
            }
        };
        mJobMapsViewModel.getNewLocations().observe(this, newLocationObserver);
        final Observer<ArrayList<JobMarker>> jobMakerObserver = new Observer<ArrayList<JobMarker>>() {
            @Override
            public void onChanged(@Nullable ArrayList<JobMarker> jobMarkers) {
                mJobMarkers = jobMarkers;
                if (mMap != null) {
                    dropMarkers();
                }
            }
        };
        mJobMapsViewModel.getJobMakers().observe(this, jobMakerObserver);
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mMapView = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.jobMaps);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (mLocationData != null) {
            drawPolyline(mLocationData, false);
        }
        dropMarkers();
    }

    private void dropMarkers() {
        if (mJobMarkers != null) {
            for (Marker marker : mMarker) {
                marker.remove();
            }
            mMarker.clear();
            JobMarker lastJobMarker = null;
            for (JobMarker jobMarker : mJobMarkers) {
                Marker marker = newMarker(jobMarker);
                mMarker.add(marker);
                lastJobMarker = jobMarker;
            }
            if (lastJobMarker != null) {
                focusOn(lastJobMarker.getJobLocation());
            }
        }
    }

    private Marker newMarker(JobMarker jobMarker) {
        MarkerOptions options = new MarkerOptions();
        options.position(jobMarker.getJobLocation());
        if (jobMarker.getJobState() == Job.DONE) {
            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.check));
        } else if (jobMarker.getJobState() == Job.PROGRESSING) {
            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.progress));
        } else if (jobMarker.getJobState() == Job.BLOCKED) {
            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.block));
        }
        return mMap.addMarker(options);
    }

    private int drawJobPolyline(ArrayList<LocationData> locationData, int startIdx, int color, boolean extend) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(POLYLINE_WIDTH);
        polylineOptions.color(color);
        if (extend && mLastLocation != null) {
            polylineOptions.add(mLastLocation);
        }
        String jobId = locationData.get(startIdx).getJobId();
        int endIdx = startIdx;
        for (; endIdx < locationData.size(); ++endIdx) {
            LocationData item = locationData.get(endIdx);
            if (!item.getJobId().equals(jobId)) {
                break;
            }
            LatLng location = new LatLng(item.getLatitude(), item.getLongitude());
            polylineOptions.add(location);
            mLastLocation = location;
        }
        mMap.addPolyline(polylineOptions);
        return endIdx;
    }


    private void drawPolyline(ArrayList<LocationData> locationData, boolean extend) {
        if (locationData.isEmpty()) {
            return;
        }
        int colorIdx = 0;
        int endIdx = drawJobPolyline(locationData, 0, mColors.get(colorIdx), extend);
        while (endIdx != locationData.size()) {
            if (++colorIdx >= mColors.size()) {
                colorIdx = 0;
            }
            endIdx = drawJobPolyline(locationData, endIdx, mColors.get(colorIdx), false);
        }
        if (mLastLocation != null) {
            focusOn(mLastLocation);
        }
    }

    private void focusOn(LatLng location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, (float) DEFAULT_ZOOM));
    }
}
