package com.sebeca.app.jobinprogress.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentSignUpBinding;


public class SignUpFragment extends ProgressFragment implements ViewModelListener {
    private FragmentSignUpBinding mBinding;
    private SignUpViewModel mViewModel;

    @Override
    public boolean allowBack() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false);
        mViewModel = new SignUpViewModel(getContext());
        mViewModel.setListener(this);
        mBinding.setViewModel(mViewModel);
        mBinding.setDataModel(mViewModel.getDataModel());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onNameError(String msg) {
        mBinding.inputName.setError(msg);
    }

    @Override
    public void onEmailError(String msg) {
        mBinding.inputEmail.setError(msg);
    }

    @Override
    public void onPasswordError(String msg) {
        mBinding.inputPassword.setError(msg);
    }

    @Override
    public void onActionProgress() {
        mBinding.signUpButton.setEnabled(false);
        showProgress(mBinding.signUpProgress, mBinding.signUpForm, true);
    }

    @Override
    public void onActionDone() {
        mBinding.signUpButton.setEnabled(true);
        showProgress(mBinding.signUpProgress, mBinding.signUpForm, false);
        FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_LOGIN, null);
    }
}
