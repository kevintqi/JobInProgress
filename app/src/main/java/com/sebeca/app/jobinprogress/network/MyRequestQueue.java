package com.sebeca.app.jobinprogress.network;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;


public class MyRequestQueue {
    static MyRequestQueue mInstance;
    RequestQueue mRequestQueue;

    private MyRequestQueue(Context context) {
        Cache cache = new DiskBasedCache(context.getApplicationContext().getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
    }

    public static synchronized MyRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyRequestQueue(context);
        }
        return mInstance;
    }

    public void start() {
        mRequestQueue.start();
    }

    public <T> void addToQueue(Request<T> req) {
        mRequestQueue.add(req);
    }
}
