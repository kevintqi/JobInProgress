package com.sebeca.app.jobinprogress.login;

import android.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * DataModel for Login Screen
 */

public class LoginModel {
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    private ObservableField<String> mEmail = new ObservableField<>();
    private ObservableField<String> mPassword = new ObservableField<>();

    public ObservableField<String> getEmail() {
        return mEmail;
    }

    public void setEmail(String v) {
        mEmail.set(v);
    }

    public ObservableField<String> getPassword() {
        return mPassword;
    }

    public void setPassword(String v) {
        mPassword.set(v);
    }

    JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(KEY_EMAIL, mEmail.get());
        obj.put(KEY_PASSWORD, mPassword.get());
        return obj;
    }
}
