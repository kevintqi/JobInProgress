package com.sebeca.app.jobinprogress.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;


public class MyArrayRequest {

    private static final String TAG = MyObjectRequest.class.getSimpleName();
    private MyJsonArrayRequest mMyJsonObjectRequest;
    private Context mContext;
    private String mUrl;
    private int mMethod;
    private Callback mCallback;

    public MyArrayRequest(Context context, String url, int method, Callback callback) {
        mContext = context;
        mUrl = url;
        mMethod = method;
        mCallback = callback;
    }

    public void send(JSONArray jsonRequest) {
        mMyJsonObjectRequest = new MyJsonArrayRequest(mMethod, mUrl, jsonRequest,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
        MyRequestQueue.getInstance(mContext).addToQueue(mMyJsonObjectRequest);
    }

    public interface Callback {
        void onSuccess(JSONArray response);

        void onError(VolleyError error);
    }

    private class MyJsonArrayRequest extends JsonArrayRequest {
        private NetworkResponse mResponse;

        public MyJsonArrayRequest(int method, String url, JSONArray jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            mResponse = response;
            showHeaders();
            return super.parseNetworkResponse(response);
        }

        public void showHeaders() {
            if (mResponse.headers != null) {
                for (String key : mResponse.headers.keySet()) {
                    Log.i(TAG, "" + key + ":" + mResponse.headers.get(key));
                }
            }
        }
    }
}
