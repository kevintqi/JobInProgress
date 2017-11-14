package com.sebeca.app.jobinprogress.data;

import android.content.Context;

import com.google.gson.Gson;

import java.net.HttpCookie;

/**
 * Persistent Data Store for Session Cookie
 */

public class SessionCookieDataStore extends DataStore {
    private static final String KEY = "session_cookie";

    public SessionCookieDataStore(Context ctx) {
        super(ctx);
    }

    public boolean isAvailable() {
        return has(KEY);
    }

    public void put(HttpCookie cookie) {
        Gson gson = new Gson();
        String data = gson.toJson(cookie);
        save(KEY, data);
    }

    public void clear() {
        remove(KEY);
    }

    public HttpCookie get() {
        String data = get(KEY);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, HttpCookie.class);
        }
    }
}
