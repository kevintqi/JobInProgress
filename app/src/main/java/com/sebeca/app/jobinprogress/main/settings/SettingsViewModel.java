package com.sebeca.app.jobinprogress.main.settings;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.data.SessionCookieDataStore;
import com.sebeca.app.jobinprogress.login.LoginActivity;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONObject;

public class SettingsViewModel {
    private static final String TAG = SettingsViewModel.class.getSimpleName();
    private Context mContext;
    private MyObjectRequest.Callback mCallBack = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            logoutAction();
        }

        @Override
        public void onError(VolleyError error) {
            logoutAction();
        }
    };

    public SettingsViewModel(Context context) {
        mContext = context;
    }

    public void onClickLogout(View view) {
        sendRequest();
    }

    private void logoutAction() {
        SessionCookieDataStore dataStore = new SessionCookieDataStore(mContext);
        dataStore.clear();
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    private void sendRequest() {
        ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
        final String url = dataStore.get() + mContext.getString(R.string.url_logout);
        MyObjectRequest request = new MyObjectRequest(mContext, url, Request.Method.GET, mCallBack);
        request.send(null);
    }
}
