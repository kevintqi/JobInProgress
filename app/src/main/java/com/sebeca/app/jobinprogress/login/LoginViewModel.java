package com.sebeca.app.jobinprogress.login;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.data.UserDataStore;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ViewModel for Login logic
 */
public class LoginViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();

    private Context mContext;
    private DataModel mDataModel = new DataModel(false);
    private ViewModelListener mListener;
    /**
     * Callback when a REST request is returned
     */
    private MyObjectRequest.Callback mCallBack = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            mListener.onActionDone(true);
            UserDataStore dataStore = new UserDataStore(mContext);
            dataStore.put(mDataModel.getEmail().get());
        }

        @Override
        public void onError(VolleyError error) {
            mListener.onActionDone(false);
            mListener.onPasswordError(mContext.getString(R.string.error_incorrect_password));
        }
    };

    public LoginViewModel(Context context) {
        mContext = context;
        UserDataStore dataStore = new UserDataStore(context);
        String user = dataStore.get();
        if (user != null) {
            mDataModel.setEmail(user);
        }
    }

    public DataModel getDataModel() {
        return mDataModel;
    }

    public void setListener(ViewModelListener Listener) {
        mListener = Listener;
    }

    /**
     * Bound to the Login action in the View
     */
    public void onClickLogin() {
        if (validateInput()) {
            sendRequest();
            mListener.onActionProgress();
        }
    }

    /**
     * Bound to the SignUp action in the view
     */
    public void onClickSignUp() {
        FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_SIGN_UP, null);
    }

    private boolean validateInput() {
        boolean valid = true;

        if (mDataModel.isValidEmail()) {
            mListener.onEmailError(null);
        } else {
            mListener.onEmailError(mContext.getString(R.string.error_invalid_email));
            valid = false;
        }

        if (mDataModel.isValidPassword()) {
            mListener.onPasswordError(null);
        } else {
            mListener.onPasswordError(mContext.getString(R.string.error_invalid_password));
            valid = false;
        }

        return valid;
    }

    private void sendRequest() {
        JSONObject data;
        try {
            data = mDataModel.toJSON();
            if (data != null) {
                ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
                final String url = dataStore.get() + mContext.getString(R.string.url_login);
                Log.i(TAG, "URL: " + url);
                MyObjectRequest request = new MyObjectRequest(mContext, url, Request.Method.POST, mCallBack);
                request.send(data);
                Log.i(TAG, "sent: " + data.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error:", e);
        }
    }
}
