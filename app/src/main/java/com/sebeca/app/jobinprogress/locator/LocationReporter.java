package com.sebeca.app.jobinprogress.locator;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.main.ActionRepeater;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A repeatable task running on the MainService to report locations
 */
public class LocationReporter extends ActionRepeater implements LocationRepository.LoadLocationsCallback {
    public static final int INTERVAL = 30000;
    private static final String LOCATION_LOGS = "locationLogs";
    private static final String TAG = LocationReporter.class.getSimpleName();
    private static final int ID = 111;
    private final Context mContext;
    @Inject
    LocationRepository mLocationRepository;
    private ArrayList<LocationData> mLocationData;

    private final MyObjectRequest.Callback mCallback = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            mLocationRepository.archiveLocations(mLocationData);
        }

        @Override
        public void onError(VolleyError error) {
        }
    };

    public LocationReporter(Context context) {
        super(ID, INTERVAL);
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        mContext = context;
    }

    @Override
    protected void action(int id) {
        if (id == ID) {
            mLocationRepository.requestLocations(this);
        }
    }

    @Override
    public void onLocationsReady(ArrayList<LocationData> locationData) {
        mLocationData = locationData;
        if (!mLocationData.isEmpty()) {
            JSONArray location = new JSONArray();
            for (LocationData item : mLocationData) {
                location.put(item.toJSON());
            }
            JSONObject data = new JSONObject();
            try {
                data.put(LOCATION_LOGS, location);
                sendRequest(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequest(JSONObject data) {
        ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
        final String url = dataStore.get() + mContext.getString(R.string.url_location);
        Log.i(TAG, "URL: " + url);
        MyObjectRequest request = new MyObjectRequest(mContext, url, Request.Method.POST, mCallback);
        request.send(data);
        Log.i(TAG, "sent: " + data.toString());
    }
}
