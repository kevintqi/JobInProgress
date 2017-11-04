package com.sebeca.app.jobinprogress.locator;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.main.ActionRepeater;
import com.sebeca.app.jobinprogress.network.MyArrayRequest;

import org.json.JSONArray;

public class LocationReporter extends ActionRepeater {
    public static final int INTERVAL = 30000;
    private static final String TAG = LocationReporter.class.getSimpleName();
    private static final int ID = 111;
    private Context mContext;
    private LocationDataQueue mLocationDataQueue;

    private MyArrayRequest.Callback mCallback = new MyArrayRequest.Callback() {

        @Override
        public void onSuccess(JSONArray response) {
        }

        @Override
        public void onError(VolleyError error) {
        }
    };

    public LocationReporter(Context context, LocationDataQueue dataQueue) {
        super(ID, INTERVAL);
        mContext = context;
        mLocationDataQueue = dataQueue;
    }

    @Override
    protected void action(int id) {
        if (id == ID) {
            Object[] items = mLocationDataQueue.popDataQueue();
            JSONArray data = buildRequest(items);
            if (data != null) {
                sendRequest(data);
            }
        }
    }

    private JSONArray buildRequest(Object[] items) {
        if (items != null && items.length != 0) {
            JSONArray data = new JSONArray();
            for (Object it : items) {
                LocationData locationData = (LocationData) it;
                data.put(locationData.toJSON());
            }
            return data;
        } else {
            Log.w(TAG, "No new location data!");
            return null;
        }
    }

    private void sendRequest(JSONArray data) {
        ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
        final String url = dataStore.get() + mContext.getString(R.string.url_location);
        Log.i(TAG, "URL: " + url);
        MyArrayRequest request = new MyArrayRequest(mContext, url, Request.Method.POST, mCallback);
        request.send(data);
        Log.i(TAG, "sent: " + data.toString());
    }
}
