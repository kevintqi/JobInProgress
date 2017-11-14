package com.sebeca.app.jobinprogress.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * Persistent Data Store for Locations
 */

public class LocationDataStore extends DataStore {
    private static final String TAG = LocationDataStore.class.getSimpleName();
    private static final String KEY = "location_list";
    private static LocationDataStore mInstance;

    private LocationDataStore(Context ctx) {
        super(ctx);
    }

    public static synchronized LocationDataStore getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new LocationDataStore(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public void put(JSONArray jobList) {
        save(KEY, jobList.toString());
    }

    public JSONArray get() {
        String data = get(KEY);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            try {
                return new JSONArray(data);
            } catch (JSONException e) {
                return null;
            }
        }
    }

    public JSONArray pop() {
        JSONArray location = get();
        remove(KEY);
        return location;
    }
}
