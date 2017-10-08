package com.sebeca.app.jobinprogress.login;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.sebeca.app.jobinprogress.R;

public class LoginViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private Context mContext;
    private LoginModel mDataModel = new LoginModel();
    private Listener mListener;
    private UserLoginTask mAuthTask = null;

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
        Log.d(TAG, "Email=" + mDataModel.getEmail().get() +
                ",Password=" + mDataModel.getPassword().get());
        if (validate()) {
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
            mListener.onLoginProgress();
        }
    }

    public void onClickSignup(View v) {
        FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_SIGNUP, null);
    }

    public boolean validate() {
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


    interface Listener {
        void onEmailError(@Nullable String msg);

        void onPasswordError(@Nullable String msg);

        void onLoginProgress();

        void onLoginDone();
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
//    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }
//
//        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
//        }
//    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mDataModel.getEmail().get())) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mDataModel.getPassword().get());
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            mListener.onLoginDone();

            if (success) {
                //finish();
                //Start MainActivity
            } else {
                mListener.onPasswordError(mContext.getString(R.string.error_incorrect_password));
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            mListener.onLoginDone();
        }
    }
}
