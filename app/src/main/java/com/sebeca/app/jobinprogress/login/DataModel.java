package com.sebeca.app.jobinprogress.login;

import android.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * DataModel for Login Screen
 */

public class DataModel {
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    private final boolean mHasName;
    private ObservableField<String> mName = new ObservableField<>();
    private ObservableField<String> mEmail = new ObservableField<>();
    private ObservableField<String> mPassword = new ObservableField<>();

    public DataModel(boolean hasName) {
        mHasName = hasName;
    }

    public final boolean hasName() {
        return mHasName;
    }

    public ObservableField<String> getName() {
        return mName;
    }

    public void setName(String v) {
        mName.set(v);
    }

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
        boolean hasData = false;
        if (mName.get() != null && !mName.get().isEmpty()) {
            obj.put(KEY_EMAIL, mName.get());
            hasData = true;
        }
        if (mEmail.get() != null && !mEmail.get().isEmpty()) {
            obj.put(KEY_EMAIL, mEmail.get());
            hasData = true;
        }
        if (mPassword.get() != null && !mPassword.get().isEmpty()) {
            obj.put(KEY_PASSWORD, mPassword.get());
            hasData = true;
        }
        return hasData ? obj : null;
    }

    boolean isValidName() {
        String name = mName.get();
        return (name != null && name.length() >= 3);
    }

    boolean isValidEmail() {
        String email = mEmail.get();
        return (email != null && !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isValidPassword() {
        String password = mPassword.get();
        return (password != null && password.length() >= 4 && password.length() <= 10);
    }
}
