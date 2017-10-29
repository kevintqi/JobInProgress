package com.sebeca.app.jobinprogress.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.net.HttpCookie;


public class PersistentDataStore {
    public static final String KEY_SESSION_COOKIE = "session_cookie";
    public static final String KEY_SERVER_URL = "server_url";
    public static final String KEY_USER = "user";
    public static final String KEY_JOB_LIST = "job_list";

    public static final String VALUE_NONE = "";
    private static final String TAG = PersistentDataStore.class.getName();
    private SharedPreferences mSharedPreferences;

    public PersistentDataStore(Context ctx) {
        mSharedPreferences = ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public void putServerUrl(String serverUrl) {
        save(KEY_SERVER_URL, serverUrl);
    }

    public String getServerUrl() {
        String data = mSharedPreferences.getString(KEY_SERVER_URL, VALUE_NONE);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, String.class);
        }
    }

    public void puUser(String user) {
        save(KEY_USER, user);
    }

    public String getUser() {
        String data = mSharedPreferences.getString(KEY_USER, VALUE_NONE);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, String.class);
        }
    }

    public void putSessionCookie(HttpCookie cookie) {
        save(KEY_SESSION_COOKIE, cookie);
    }

    public HttpCookie getSessionCookie() {
        String data = mSharedPreferences.getString(KEY_SESSION_COOKIE, VALUE_NONE);
        if (data.equals(VALUE_NONE)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, HttpCookie.class);
        }
    }

    private <T> void save(String key, T data) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(data);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, jsonString);
        editor.apply();
    }
}
