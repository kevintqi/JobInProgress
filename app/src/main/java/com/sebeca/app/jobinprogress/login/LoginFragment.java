package com.sebeca.app.jobinprogress.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentLoginBinding;


public class LoginFragment extends BaseFragment implements LoginViewModel.Listener {
    private FragmentLoginBinding mBinding;
    private LoginViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false);
        mViewModel = new LoginViewModel(getContext());
        mViewModel.setListener(this);
        mBinding.setViewModel(mViewModel);
        mBinding.setDataModel(mViewModel.getDataModel());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public boolean allowBack() {
        return false;
    }

    @Override
    public void onEmailError(@Nullable String msg) {
        mBinding.inputEmail.setError(msg);
    }

    @Override
    public void onPasswordError(@Nullable String msg) {
        mBinding.inputPassword.setError(msg);
    }

    @Override
    public void onLoginProgress() {
        mBinding.signInButton.setEnabled(false);
        showProgress(true);
    }

    @Override
    public void onLoginDone() {
        mBinding.signInButton.setEnabled(true);
        showProgress(false);
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mBinding.loginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mBinding.loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
