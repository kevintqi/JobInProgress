package com.sebeca.app.jobinprogress.data;


import android.content.Context;

/**
 * Persistent store for the current active job ID
 */

public class ActiveJobDataStore extends DataStore {
    private static final String TAG = ActiveJobDataStore.class.getSimpleName();
    private static final String KEY = "active_job";
    private Callback mCallback;

    public ActiveJobDataStore(Context ctx) {
        super(ctx);
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
