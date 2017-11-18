package com.sebeca.app.jobinprogress.data;


import android.content.Context;

/**
 * Persistent store for the current active job ID
 */

public class ActiveJobDataStore extends DataStore {
    private static final String TAG = ActiveJobDataStore.class.getSimpleName();
    private static final String KEY = "active_job";
    private static ActiveJobDataStore mInstance;
    private Callback mCallback;

    private ActiveJobDataStore(Context ctx) {
        super(ctx);
    }

    public static synchronized ActiveJobDataStore getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new ActiveJobDataStore(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void put(String jobId) {
        if (mCallback != null) {
            mCallback.onUpdate(jobId);
        }
        save(KEY, jobId);
    }

    public String get() {
        String data = get(KEY);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            return data;
        }
    }

    public interface Callback {
        void onUpdate(String jobId);
    }
}
