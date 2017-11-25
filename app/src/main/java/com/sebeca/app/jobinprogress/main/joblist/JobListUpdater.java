package com.sebeca.app.jobinprogress.main.joblist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.data.SessionCookieDataStore;
import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.login.LoginActivity;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;
import com.sebeca.app.jobinprogress.util.ActionRepeater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Request Job List from Cloud
 */

public class JobListUpdater extends ActionRepeater {
    private static final int INTERVAL = 300000;
    private static final int ID = 101;
    private static final String TAG = JobListUpdater.class.getSimpleName();
    private final Context mContext;
    @Inject
    JobListRepository mJobListRepository;
    private final MyObjectRequest.Callback mCallback = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            try {
                JSONArray jobList = response.getJSONArray("jobs");
                refreshJobList(jobList);
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

    public JobListUpdater(Context context) {
        super(ID, INTERVAL);
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
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

    private void refreshJobList(JSONArray jobList) {
        if (jobList != null) {
            ArrayList<Job> jobs = new ArrayList<>();
            for (int i = 0; i < jobList.length(); ++i) {
                Job job = newJob(jobList, i);
                if (job != null) {
                    jobs.add(job);
                }
            }
            mJobListRepository.requestUpdateJobList(jobs);
        }
    }

    private Job newJob(JSONArray jobList, int index) {
        try {
            JSONObject data = jobList.getJSONObject(index);
            return new Job(data, index);
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
        return null;
    }
}
