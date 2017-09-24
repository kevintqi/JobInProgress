package com.sebeca.app.jobinprogress.locator;

import android.content.Context;
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

    private RequestQueue mRequestQueue;

    LocationReporter(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void reportData(Object[] items) {
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
}
