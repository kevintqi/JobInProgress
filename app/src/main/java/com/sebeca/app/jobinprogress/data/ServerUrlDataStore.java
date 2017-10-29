package com.sebeca.app.jobinprogress.data;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Persistent Data Store for Server URL
 */

public class ServerUrlDataStore extends DataStore {
    private static final String KEY = "server_url";

    public ServerUrlDataStore(Context ctx) {
        super(ctx);
    }

    public boolean isAvailable() {
        return has(KEY);
    }

    public void put(String serverUrl) {
        save(KEY, serverUrl);
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
