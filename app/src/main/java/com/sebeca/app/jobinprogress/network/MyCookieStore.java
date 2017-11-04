package com.sebeca.app.jobinprogress.network;

import android.content.Context;
import android.util.Log;

import com.sebeca.app.jobinprogress.data.SessionCookieDataStore;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * A Wrapper of the default InMemoryCookieStore which persists session cookies to
 * the SharedPreference.
 * <p>
 * Decorator Pattern
 */

public class MyCookieStore implements CookieStore {
    private static final String TAG = MyCookieStore.class.getSimpleName();
    private Context mContext;
    private CookieStore mStore = (new CookieManager()).getCookieStore();
    private SessionCookieDataStore mDataStore;

    public MyCookieStore(Context context) {
        // prevent context leaking by getting the application context
        mContext = context.getApplicationContext();
        mDataStore = new SessionCookieDataStore(mContext);

        HttpCookie cookie = mDataStore.get();
        if (cookie != null) {
            mStore.add(URI.create(cookie.getDomain()), cookie);
        }
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        Log.i(TAG, cookie.toString());
        Log.i(TAG, cookie.getName());
        Log.i(TAG, cookie.getDomain());
        remove(URI.create(cookie.getDomain()), cookie);
        mDataStore.put(cookie);
        mStore.add(URI.create(cookie.getDomain()), cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return mStore.get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        return mStore.getCookies();
    }

    @Override
    public List<URI> getURIs() {
        return mStore.getURIs();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return mStore.remove(uri, cookie);
    }

    @Override
    public boolean removeAll() {
        return mStore.removeAll();
    }
}
