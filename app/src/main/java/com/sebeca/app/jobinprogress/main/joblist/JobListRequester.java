package com.sebeca.app.jobinprogress.main.joblist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.JobListDataStore;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.data.SessionCookieDataStore;
import com.sebeca.app.jobinprogress.login.LoginActivity;
import com.sebeca.app.jobinprogress.main.ActionRepeater;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JobListRequester extends ActionRepeater {
    public static final int INTERVAL = 300000;
    private static final String TAG = JobListRequester.class.getSimpleName();
    private static final int ID = 101;
    private Context mContext;

    private MyObjectRequest.Callback mCallback = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            try {
                Log.i(TAG, response.toString());
                JSONArray jobList = response.getJSONArray("jobs");
                JobListDataStore dataStore = new JobListDataStore(mContext);
                dataStore.put(jobList);
            } catch (JSONException e) {
                Log.wtf(TAG, e);
            }
        }

        @Override
        public void onError(VolleyError error) {
            if (error instanceof AuthFailureError) {
                SessionCookieDataStore dataStore = new SessionCookieDataStore(mContext);
                dataStore.clear();
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        }
    };

    public JobListRequester(Context context) {
        super(ID, INTERVAL);
        mContext = context;
    }

    @Override
    protected void action(int id) {
        if (id == ID) {
            ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
            final String url = dataStore.get() + mContext.getString(R.string.url_jobs);
            Log.i(TAG, "URL: " + url);
            MyObjectRequest request = new MyObjectRequest(mContext, url, Request.Method.GET, mCallback);
            request.send(null);
        }
    }
}
