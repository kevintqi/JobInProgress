package com.sebeca.app.jobinprogress.login;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();

    private static final String URL = "https://www.sebeca.com/login";

    private Context mContext;
    private LoginModel mDataModel = new LoginModel();
    private ViewModelListener mListener;
    private MyObjectRequest.Callback mCallBack = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            mListener.onActionDone();
        }

        @Override
        public void onError(VolleyError error) {
            mListener.onActionDone();
            mListener.onPasswordError(mContext.getString(R.string.error_incorrect_password));
        }
    };

    public LoginViewModel(Context context) {
        mContext = context;
    }

    public LoginModel getDataModel() {
        return mDataModel;
    }

    public void setListener(ViewModelListener Listener) {
        mListener = Listener;
    }

    public void onClickLogin(View v) {
        if (validateInput()) {
            sendRequest();
            mListener.onActionProgress();
        }
    }

    public void onClickSignUp(View v) {
        FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_SIGNUP, null);
    }

    private boolean validateInput() {
        boolean valid = true;

        if (mDataModel.isValidEmail()) {
            mListener.onEmailError(null);
        } else {
            mListener.onEmailError("enter a valid email address");
            valid = false;
        }

        if (mDataModel.isValidPassword()) {
            mListener.onPasswordError(null);
        } else {
            mListener.onPasswordError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }

    private void sendRequest() {
        JSONObject data;
        try {
            data = mDataModel.toJSON();
            if (data != null) {
                MyObjectRequest request = new MyObjectRequest(mContext, URL, Request.Method.POST, mCallBack);
                request.send(data);
                Log.i(TAG, "sent: " + data.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error:", e);
        }
    }
}
