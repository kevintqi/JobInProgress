package com.sebeca.app.jobinprogress.login;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;
import com.sebeca.app.jobinprogress.network.PersistentDataStore;

import org.json.JSONObject;

public class SignUpViewModel {
    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private Context mContext;
    private DataModel mDataModel = new DataModel(true);
    private ViewModelListener mListener;
    private MyObjectRequest.Callback mCallback = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            mListener.onActionDone(true);
        }

        @Override
        public void onError(VolleyError error) {
            mListener.onActionDone(false);
        }
    };

    public SignUpViewModel(Context context) {
        mContext = context;
    }

    public void setListener(ViewModelListener listener) {
        mListener = listener;
    }

    public DataModel getDataModel() {
        return mDataModel;
    }

    public void onClickSignUp() {
        if (validateInput()) {
            sendRequest();
            mListener.onActionProgress();
        }
    }

    public void onClickLogin() {
        mListener.onActionDone(true);
    }

    private boolean validateInput() {
        boolean valid = true;
        if (mDataModel.isValidName()) {
            mListener.onNameError(null);
        } else {
            mListener.onNameError(mContext.getString(R.string.error_invalid_name));
            valid = false;
        }
        if (mDataModel.isValidEmail()) {
            mListener.onEmailError(null);
        } else {
            mListener.onEmailError(mContext.getString(R.string.error_invalid_email));
            valid = false;
        }
        if (mDataModel.isValidName()) {
            mListener.onPasswordError(null);
        } else {
            mListener.onPasswordError(mContext.getString(R.string.error_invalid_password));
            valid = false;
        }
        return valid;
    }

    private void sendRequest() {
        try {
            JSONObject data = mDataModel.toJSON();
            if (data != null) {
                PersistentDataStore dataStore = new PersistentDataStore(mContext);
                final String url = dataStore.getServerUrl() + mContext.getString(R.string.url_sign_up);
                Log.i(TAG, "URL: " + url);
                MyObjectRequest request = new MyObjectRequest(mContext, url, Request.Method.POST, mCallback);
                request.send(data);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error:", e);
        }
    }
}
