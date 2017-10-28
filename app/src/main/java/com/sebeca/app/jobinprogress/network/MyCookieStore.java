package com.sebeca.app.jobinprogress.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

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
    /**
     * The preferences name.
     */
    private final static String PREFS_NAME = MyCookieStore.class.getName();

    /**
     * The default preferences string.
     */
    private final static String KEY_NONE = "";

    /**
     * The preferences session cookie key.
     */
    private final static String KEY_SESSION_COOKIE = "session_cookie";

    private Context mContext;

    private CookieStore mStore = (new CookieManager()).getCookieStore();

    public MyCookieStore(Context context) {
        // prevent context leaking by getting the application context
        mContext = context.getApplicationContext();

        //if there is a cookie stored in shared preferences,
        // we added it to the cookie store
        String sessionCookie = getSavedSessionCookie();
        if (!sessionCookie.equals(KEY_NONE)) {
            Gson gson = new Gson();
            HttpCookie cookie = gson.fromJson(sessionCookie, HttpCookie.class);
            mStore.add(URI.create(cookie.getDomain()), cookie);
        }
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        if (cookie.getName().equals("sessionid")) {
            // if the cookie that the cookie store attempt to add is a session cookie,
            // we remove the older cookie and save the new one in shared preferences
            remove(URI.create(cookie.getDomain()), cookie);
            saveSessionCookie(cookie);
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

    private String getSavedSessionCookie() {
        return getPrefs().getString(KEY_SESSION_COOKIE, KEY_NONE);
    }

    /**
     * Saves the HttpCookie to SharedPreferences as a json string.
     *
     * @param cookie The cookie to save in SharedPreferences.
     */
    private void saveSessionCookie(HttpCookie cookie) {
        Gson gson = new Gson();
        String jsonSessionCookieString = gson.toJson(cookie);
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(KEY_SESSION_COOKIE, jsonSessionCookieString);
        editor.apply();
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
