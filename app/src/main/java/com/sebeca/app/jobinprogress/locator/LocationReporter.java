package com.sebeca.app.jobinprogress.locator;

import android.app.IntentService;
import android.content.Intent;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;


public class LocationReporter extends IntentService {
    private static final String TAG = LocationReporter.class.getSimpleName();
    private static final String URL = "https://www.sebeca.com/location";
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 10f;
    public static final int IDLE = 30000;
    private final LocationUpdater mLocationUpdater = new LocationUpdater();
    private RequestQueue mRequestQueue;
    private boolean mShouldContinue = true;

    public LocationReporter() {
        super(TAG);
        mRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationUpdater
            );
        } catch (java.lang.SecurityException e) {
            Log.e(TAG, "fail to request location update, ignore", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "network provider does not exist, " + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        mShouldContinue = false;
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.removeUpdates(mLocationUpdater);
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // intent - start
        // intent - pause
        // intent - resume
        // intent - stop
        while (mShouldContinue) {
            try {
                Thread.sleep(IDLE);
                JSONArray data = buildRequest();
                if (data != null) {
                    sendRequest(data);
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "Service Interrupted! ", e);
            }
        }
    }

    private JSONArray buildRequest() {
        LocationData[] items = mLocationUpdater.popItemQueue();
        if (items != null) {
            JSONArray data = new JSONArray();
            for (LocationData i : items) {
                data.put(i.toJSON());
            }
            return data;
        } else {
            Log.w(TAG, "No new location data!");
            return null;
        }
    }

    private void sendRequest(JSONArray data) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
        mRequestQueue.add(request);
        Log.i(TAG, "sent: " + data.toString());
    }
}
