package com.sebeca.app.jobinprogress.network;

import android.app.Application;

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
    private static final int CACHE_SIZE = 1024 * 1024;
    private static final String TAG = "SEBECA";
    private RequestQueue mRequestQueue;
    private MyCookieStore myCookieStore;

    public MyRequestQueue(Application app) {
        myCookieStore = new MyCookieStore(app);
        CookieHandler.setDefault(new CookieManager(myCookieStore, CookiePolicy.ACCEPT_ALL));
        Cache cache = new DiskBasedCache(app.getCacheDir(), CACHE_SIZE);
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        ServerUrlDataStore dataStore = new ServerUrlDataStore(app);
        if (!dataStore.isAvailable()) {
            String serverUrl = app.getString(R.string.server_url);
            dataStore.put(serverUrl);
        }
    }

    public void start() {
        mRequestQueue.start();
    }

    <T> void addToQueue(Request<T> req) {
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

    public void cancelAll() {
        mRequestQueue.cancelAll(TAG);
    }

}
