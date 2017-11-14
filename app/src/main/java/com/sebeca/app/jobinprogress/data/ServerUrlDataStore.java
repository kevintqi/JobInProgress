package com.sebeca.app.jobinprogress.data;

import android.content.Context;

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

    public void clear() {
        remove(KEY);
    }

    public String get() {
        return get(KEY);
    }
}
