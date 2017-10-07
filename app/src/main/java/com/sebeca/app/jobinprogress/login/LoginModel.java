package com.sebeca.app.jobinprogress.login;

import android.databinding.ObservableField;

/**
 * Created by kevinqi on 10/5/17.
 */

public class LoginModel {
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
}
