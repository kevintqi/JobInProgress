package com.sebeca.app.jobinprogress.network;

import android.content.Context;
import android.util.Log;

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
    private PersistentDataStore mDataStore;

    public MyCookieStore(Context context) {
        // prevent context leaking by getting the application context
        mContext = context.getApplicationContext();
        mDataStore = new PersistentDataStore(mContext);

        HttpCookie cookie = mDataStore.getSessionCookie();
        if (cookie != null) {
            mStore.add(URI.create(cookie.getDomain()), cookie);
        }
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        Log.i(TAG, cookie.toString());
        if (cookie.getName().equals("sessionid")) {
            // if the cookie that the cookie store attempt to add is a session cookie,
            // we remove the older cookie and save the new one in shared preferences
            remove(URI.create(cookie.getDomain()), cookie);
            mDataStore.putSessionCookie(cookie);
        }
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
