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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.locator.LocationData;

import java.util.ArrayList;

/**
 * Map View of a Job
 */
public class JobMapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = JobMapsFragment.class.getSimpleName();
    private SupportMapFragment mMapView;
    private GoogleMap mMap;
    private JobMapsViewModel mJobMapsViewModel;
    private ArrayList<LocationData> mLocationData;
    private Polyline mPolyline;
    private ArrayList<JobMarker> mJobMarkers;
    private final ArrayList<Marker> mMarker = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobMapsViewModel = ViewModelProviders.of(this).get(JobMapsViewModel.class);
        final Observer<ArrayList<LocationData>> locationDataObserver = new Observer<ArrayList<LocationData>>() {
            @Override
            public void onChanged(@Nullable ArrayList<LocationData> locationData) {
                mLocationData = locationData;
                if (mMap != null) {
                    drawPolyline();
                }
            }
        };
        mJobMapsViewModel.getLocationData().observe(this, locationDataObserver);
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
        mMapView = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.jobMaps);
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
        drawPolyline();
        dropMarkers();
    }

    private void dropMarkers() {
        if (mJobMarkers != null) {
            if (mMarker != null) {
                for (Marker marker : mMarker) {
                    marker.remove();
                }
                mMarker.clear();
            }
            JobMarker lastJobMarker = null;
            for (JobMarker jobMarker : mJobMarkers) {
                MarkerOptions options = new MarkerOptions();
                options.position(jobMarker.getJobLocation());
                options.title(jobMarker.getJobState());
                Marker marker = mMap.addMarker(options);
                mMarker.add(marker);
                lastJobMarker = jobMarker;
            }
            if (lastJobMarker != null) {
                focusOn(lastJobMarker.getJobLocation());
            }
        }
    }

    private void drawPolyline() {
        if (mLocationData != null) {
            if (mPolyline != null) {
                mPolyline.remove();
            }
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.BLUE);
            polylineOptions.width(5);
            polylineOptions.clickable(true);
            LatLng location = null;
            for (LocationData item : mLocationData) {
                location = new LatLng(item.getLatitude(), item.getLongitude());
                polylineOptions.add(location);
            }
            mPolyline = mMap.addPolyline(polylineOptions);
            if (location != null) {
                focusOn(location);
            }
        }
    }

    private void focusOn(LatLng location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, (float) 15.0));
    }
}
