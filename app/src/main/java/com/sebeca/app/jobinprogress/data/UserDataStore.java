package com.sebeca.app.jobinprogress.data;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Persistent Data Store for Login Name
 */

public class UserDataStore extends DataStore {
    private static final String KEY = "user";

    public UserDataStore(Context ctx) {
        super(ctx);
    }

    public boolean isAvailable() {
        return has(KEY);
    }

    public void put(String user) {
        save(KEY, user);
    }

    public String get() {
        String data = get(KEY);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, String.class);
        }
    }
}
