package com.sebeca.app.jobinprogress.login;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.network.MyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();

    private static final String URL = "https://www.sebeca.com/login";

    private Context mContext;
    private LoginModel mDataModel = new LoginModel();
    private Listener mListener;

    public LoginViewModel(Context context) {
        mContext = context;
    }

    public LoginModel getDataModel() {
        return mDataModel;
    }

    public void setListener(Listener Listener) {
        mListener = Listener;
    }

    public void onClickLogin(View v) {
        if (validateInput()) {
            sendRequest();
            mListener.onLoginProgress();
        }
    }

    public void onClickSignup(View v) {
        FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_SIGNUP, null);
    }

    private boolean validateInput() {
        boolean valid = true;

        String email = mDataModel.getEmail().get();
        String password = mDataModel.getPassword().get();

        if (email == null || email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mListener.onEmailError("enter a valid email address");
            valid = false;
        } else {
            mListener.onEmailError(null);
        }

        if (password == null || password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mListener.onPasswordError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mListener.onPasswordError(null);
        }

        return valid;
    }

    private void sendRequest() {
        JSONObject data;
        try {
            data = mDataModel.toJSON();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, response.toString());
                            mListener.onLoginDone();
                            //stat main activity
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.toString());
                    mListener.onLoginDone();
                    mListener.onPasswordError(mContext.getString(R.string.error_incorrect_password));
                }
            });
            MyRequestQueue.getInstance(mContext).addToQueue(request);
            Log.i(TAG, "sent: " + data.toString());
        } catch (JSONException e) {
            Log.e(TAG, "Error:", e);
        }
    }

    interface Listener {
        void onEmailError(@Nullable String msg);

        void onPasswordError(@Nullable String msg);

        void onLoginProgress();

        void onLoginDone();
    }
}
