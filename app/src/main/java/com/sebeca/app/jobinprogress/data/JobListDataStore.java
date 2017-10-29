package com.sebeca.app.jobinprogress.data;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Persistent Data Store for Job List
 */

public class JobListDataStore extends DataStore {
    private static final String KEY = "job_list";

    public JobListDataStore(Context ctx) {
        super(ctx);
    }

    public boolean isAvailable() {
        return has(KEY);
    }

    public void put(JSONObject jobList) {
        save(KEY, jobList);
    }

    public JSONObject get() {
        String data = get(KEY);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, JSONObject.class);
        }
    }
}
