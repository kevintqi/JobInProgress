package com.sebeca.app.jobinprogress.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sebeca.app.jobinprogress.di.App;

import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Wrapper for volley.toolbox.JsonObjectRequest - hopefully it is a bit easier to use
 */

public class MyObjectRequest {
    private static final String TAG = MyObjectRequest.class.getSimpleName();
    @Inject
    MyRequestQueue mMyRequestQueue;
    private MyJsonObjectRequest mMyJsonObjectRequest;
    private Context mContext;
    private String mUrl;
    private int mMethod;
    private Callback mCallback;

    public MyObjectRequest(Context context, String url, int method, Callback callback) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        mContext = context;
        mUrl = url;
        mMethod = method;
        mCallback = callback;
    }

    public void send(JSONObject jsonRequest) {
        mMyJsonObjectRequest = new MyJsonObjectRequest(mMethod, mUrl, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        mCallback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        mCallback.onError(error);
                    }
                });
        mMyRequestQueue.addToQueue(mMyJsonObjectRequest);
    }

    public interface Callback {
        void onSuccess(JSONObject response);

        void onError(VolleyError error);
    }

    private class MyJsonObjectRequest extends JsonObjectRequest {
        private NetworkResponse mResponse;

        MyJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            mResponse = response;
            showHeaders();
            return super.parseNetworkResponse(response);
        }

        void showHeaders() {
            if (mResponse.headers != null) {
                for (String key : mResponse.headers.keySet()) {
                    Log.i(TAG, "" + key + ":" + mResponse.headers.get(key));
                }
            }
        }
    }
}
