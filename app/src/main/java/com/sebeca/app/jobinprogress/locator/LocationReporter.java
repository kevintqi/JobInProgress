package com.sebeca.app.jobinprogress.locator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.network.MyArrayRequest;
import com.sebeca.app.jobinprogress.network.PersistentDataStore;

import org.json.JSONArray;


public class LocationReporter {
    private static final String TAG = LocationReporter.class.getSimpleName();

    private static final int MSG_ID = 101;
    private static final int MSG_DELAY = 30000;
    private Context mContext;
    private boolean mCancelled = false;
    private Handler mHandler = new SenderHandler();
    private LocationDataQueue mLocationDataQueue;
    private MyArrayRequest.Callback mCallback = new MyArrayRequest.Callback() {

        @Override
        public void onSuccess(JSONArray response) {
        }

        @Override
        public void onError(VolleyError error) {
        }
    };

    LocationReporter(Context context, LocationDataQueue dataQueue) {
        mContext = context;
        mLocationDataQueue = dataQueue;
    }

    public synchronized void start() {
        mCancelled = false;
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_ID), MSG_DELAY);
    }

    public synchronized void cancel() {
        mCancelled = true;
    }

    void reportData() {
        Object[] items = mLocationDataQueue.popDataQueue();
        JSONArray data = buildRequest(items);
        if (data != null) {
            sendRequest(data);
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
        PersistentDataStore dataStore = new PersistentDataStore(mContext);
        final String url = dataStore.getServerUrl() + mContext.getString(R.string.url_location);
        Log.i(TAG, "URL: " + url);
        MyArrayRequest request = new MyArrayRequest(mContext, url, Request.Method.POST, mCallback);
        request.send(data);
        Log.i(TAG, "sent: " + data.toString());
    }

    private class SenderHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            synchronized (LocationReporter.this) {
                if (mCancelled) {
                    return;
                }
                reportData();
                sendMessageDelayed(obtainMessage(MSG_ID), MSG_DELAY);
            }
        }
    }
}
