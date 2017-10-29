package com.sebeca.app.jobinprogress.network;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;


public class MyRequestQueue {
    private static final String TAG = "SEBECA";
    static MyRequestQueue mInstance;
    RequestQueue mRequestQueue;
    MyCookieStore myCookieStore;

    private MyRequestQueue(Context context) {
        myCookieStore = new MyCookieStore(context);
        CookieHandler.setDefault(new CookieManager(myCookieStore, CookiePolicy.ACCEPT_ALL));
        Cache cache = new DiskBasedCache(context.getApplicationContext().getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        ServerUrlDataStore dataStore = new ServerUrlDataStore(context);
        if (!dataStore.isAvailable()) {
            String serverUrl = context.getString(R.string.server_url);
            dataStore.put(serverUrl);
        }
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
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

    public void cancelAll() {
        mRequestQueue.cancelAll(TAG);
    }

    public boolean hasAuthenticated() {
        return false;
    }
}
