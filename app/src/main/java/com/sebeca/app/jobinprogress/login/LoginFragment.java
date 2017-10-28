package com.sebeca.app.jobinprogress.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentLoginBinding;
import com.sebeca.app.jobinprogress.main.MainActivity;

/**
 * Login Screen
 */
public class LoginFragment extends ProgressFragment implements ViewModelListener {
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
        mBinding.setViewModelSwitchServer(new SwitchServerViewModel());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public boolean allowBack() {
        return true;
    }

    @Override
    public void onNameError(@Nullable String ignore) {
    }

    @Override
    public void onEmailError(@Nullable String msg) {
        mBinding.inputDataForm.inputEmail.setError(msg);
    }

    @Override
    public void onPasswordError(@Nullable String msg) {
        mBinding.inputDataForm.inputPassword.setError(msg);
    }

    @Override
    public void onActionProgress() {
        mBinding.loginButton.setEnabled(false);
        showProgress(mBinding.loginProgress, mBinding.loginForm, true);
    }

    /**
     * Stop the progressbar and start the MainActivity
     */
    @Override
    public void onActionDone(boolean success) {
        mBinding.loginButton.setEnabled(true);
        showProgress(mBinding.loginProgress, mBinding.loginForm, false);
        if (success) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(intent);
        }
    }
}
