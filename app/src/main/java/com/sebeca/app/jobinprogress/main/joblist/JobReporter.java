package com.sebeca.app.jobinprogress.main.joblist;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONObject;

public class JobReporter {
    private static final String TAG = JobReporter.class.getSimpleName();
    private final MyObjectRequest.Callback mCallback = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
        }

        @Override
        public void onError(VolleyError error) {
        }
    };
    private Context mContext;

    JobReporter(Context context) {
        mContext = context;
    }

    public void sendRequest(Job job) {
        JSONObject data = job.toJSON();
        if (data != null) {
            ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
            final String url = dataStore.get() + "/api/job/" + job.getId() + "/update";
            Log.i(TAG, "URL: " + url);
            MyObjectRequest request = new MyObjectRequest(mContext, url, Request.Method.PUT, mCallback);
            request.send(data);
            Log.i(TAG, "sent: " + data.toString());
        }
    }
}
