package com.sebeca.app.jobinprogress.login;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SignupViewModel {
    private static final String TAG = SignupViewModel.class.getSimpleName();
    private Context mContext;
    private SignupModel mDataModel = new SignupModel();
    private Listener mListener;

    public SignupViewModel(Context context) {
        mContext = context;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void onSignupClick(View button) {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        button.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();

//        String name = _nameText.getText().toString();
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        //progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginLinkClick(View v) {

    }

    public void onSignupSuccess() {
//        _signupButton.setEnabled(true);
//        setResult(RESULT_OK, null);
//        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(mContext, "Login failed", Toast.LENGTH_LONG).show();

//        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mDataModel.getName().get();
        String email = mDataModel.getEmail().get();
        String password = mDataModel.getPassword().get();

        if (name.isEmpty() || name.length() < 3) {
            //_nameText.setError("at least 3 characters");
            mListener.onNameError("at least 3 characters");
            valid = false;
        } else {
            //_nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //_emailText.setError("enter a valid email address");
            mListener.onEmailError("enter a valid email address");
            valid = false;
        } else {
            //_emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            //_passwordText.setError("between 4 and 10 alphanumeric characters");
            mListener.onPasswordError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            //_passwordText.setError(null);
        }

        return valid;
    }

    interface Listener {
        void onNameError(String msg);

        void onEmailError(String msg);

        void onPasswordError(String msg);
    }
}
