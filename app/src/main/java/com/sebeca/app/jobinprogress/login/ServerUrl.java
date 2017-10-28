package com.sebeca.app.jobinprogress.login;

import android.databinding.ObservableField;

public class ServerUrl {
    private ObservableField<String> mUrl = new ObservableField<>();

    public ObservableField<String> getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl.set(url);
    }
}
