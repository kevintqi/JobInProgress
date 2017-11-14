package com.sebeca.app.jobinprogress.data;

import android.content.Context;

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

    public void clear() {
        remove(KEY);
    }

    public String get() {
        return get(KEY);
    }
}
