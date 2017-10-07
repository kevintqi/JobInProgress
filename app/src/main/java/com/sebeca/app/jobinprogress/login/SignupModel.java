package com.sebeca.app.jobinprogress.login;

import android.databinding.ObservableField;

public class SignupModel {
    private ObservableField<String> mName = new ObservableField<>();
    private ObservableField<String> mEmail = new ObservableField<>();
    private ObservableField<String> mPassword = new ObservableField<>();

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

}
