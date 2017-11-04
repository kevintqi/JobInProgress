package com.sebeca.app.jobinprogress.data;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;

/**
 * Persistent Data Store for Job List
 */

public class JobListDataStore extends DataStore {

    private static final String KEY = "job_list";
    private Callback mCallback;

    public JobListDataStore(Context ctx) {
        super(ctx);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public boolean isAvailable() {
        return has(KEY);
    }

    public void put(JSONArray jobList) {
        if (mCallback != null) {
            mCallback.onUpdate(jobList);
        }
        save(KEY, jobList);
    }

    public JSONArray get() {
        String data = get(KEY);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, JSONArray.class);
        }
    }

    public interface Callback {
        void onUpdate(JSONArray jobList);
    }
}
