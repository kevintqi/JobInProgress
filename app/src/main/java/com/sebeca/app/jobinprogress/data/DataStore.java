package com.sebeca.app.jobinprogress.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.sebeca.app.jobinprogress.di.App;

import javax.inject.Inject;

public class DataStore {
    static final String VALUE_NONE = "";
    @Inject
    SharedPreferences mSharedPreferences;

    DataStore(Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
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
