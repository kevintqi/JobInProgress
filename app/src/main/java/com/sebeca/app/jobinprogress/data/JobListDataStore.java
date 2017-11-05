package com.sebeca.app.jobinprogress.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

/**
 * Persistent Data Store for Job List
 */

public class JobListDataStore extends DataStore {
    private static final String TAG = JobListDataStore.class.getSimpleName();
    private static final String KEY = "job_list";
    private static JobListDataStore mInstance;
    private Callback mCallback;

    private JobListDataStore(Context ctx) {
        super(ctx);
    }

    public static synchronized JobListDataStore getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new JobListDataStore(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void put(JSONArray jobList) {
        if (mCallback != null) {
            mCallback.onUpdate(jobList);
        }
        Log.i(TAG, jobList.toString());
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
