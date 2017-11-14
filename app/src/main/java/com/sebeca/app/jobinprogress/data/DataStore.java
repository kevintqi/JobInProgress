package com.sebeca.app.jobinprogress.data;

import android.content.Context;
import android.content.SharedPreferences;

class DataStore {
    static final String VALUE_NONE = "";
    private static final String TAG = DataStore.class.getName();
    private final SharedPreferences mSharedPreferences;

    DataStore(Context ctx) {
        mSharedPreferences = ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    boolean has(String key) {
        return mSharedPreferences.contains(key);
    }

    String get(String key) {
        return mSharedPreferences.getString(key, VALUE_NONE).replaceAll("^\"|\"$", "");
    }

    void remove(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    void save(String key, String data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }
}
