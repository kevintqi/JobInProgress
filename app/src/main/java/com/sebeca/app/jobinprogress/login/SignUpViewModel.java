package com.sebeca.app.jobinprogress.login;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONObject;

public class SignUpViewModel {
    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private static final String URL = "http://www.google.com";
    private Context mContext;
    private LoginModel mDataModel = new LoginModel();
    private ViewModelListener mListener;
    private MyObjectRequest.Callback mCallback = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            mListener.onActionDone();
        }

        @Override
        public void onError(VolleyError error) {
            mListener.onActionDone();
            Toast.makeText(mContext, "Sign up failed. Please try again.", Toast.LENGTH_LONG).show();
        }
    };

    public SignUpViewModel(Context context) {
        mContext = context;
    }

    public void setListener(ViewModelListener listener) {
        mListener = listener;
    }

    public LoginModel getDataModel() {
        return mDataModel;
    }

    public void onClickSignUp(View button) {
        Log.d(TAG, "SignUp");

        if (validateInput()) {
            sendRequest();
            mListener.onActionProgress();
        }
    }

    public void onClickLogin(View v) {
        mListener.onActionDone();
    }

    private boolean validateInput() {
        boolean valid = true;
        if (mDataModel.isValidName()) {
            mListener.onNameError(null);
        } else {
            mListener.onNameError("at least 3 characters");
            valid = false;
        }
        if (mDataModel.isValidEmail()) {
            mListener.onEmailError(null);
        } else {
            mListener.onEmailError("enter a valid email address");
            valid = false;
        }
        if (mDataModel.isValidName()) {
            mListener.onPasswordError(null);
        } else {
            mListener.onPasswordError("between 4 and 10 alphanumeric characters");
            valid = false;
        }
        return valid;
    }

    private void sendRequest() {
        try {
            JSONObject data = new JSONObject();
            MyObjectRequest request = new MyObjectRequest(mContext, URL, Request.Method.GET, mCallback);
            request.send(data);
        } catch (Exception e) {
            Log.e(TAG, "Error:", e);
        }
    }
}
