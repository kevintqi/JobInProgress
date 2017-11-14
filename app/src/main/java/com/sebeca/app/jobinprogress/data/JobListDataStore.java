package com.sebeca.app.jobinprogress.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

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

    public interface Callback {
        void onUpdate(JSONArray jobList);
    }
}
