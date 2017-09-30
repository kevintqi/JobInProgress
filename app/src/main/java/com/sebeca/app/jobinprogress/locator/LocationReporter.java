package com.sebeca.app.jobinprogress.locator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;


public class LocationReporter {
    private static final String TAG = LocationReporter.class.getSimpleName();

    private static final String URL = "https://www.sebeca.com/location";
    private static final int MSG_ID = 101;
    private static final int MSG_DELAY = 30000;
    private RequestQueue mRequestQueue;
    private boolean mCancelled = false;
    private Handler mHandler = new SenderHandler();
    private LocationDataQueue mLocationDataQueue;

    LocationReporter(Context context, LocationDataQueue dataQueue) {
        mRequestQueue = Volley.newRequestQueue(context);
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
            for (Object i : items) {
                LocationData l = (LocationData) i;
                data.put(l.toJSON());
            }
            return data;
        } else {
            Log.w(TAG, "No new location data!");
            return null;
        }
    }

    private void sendRequest(JSONArray data) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
        mRequestQueue.add(request);
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
