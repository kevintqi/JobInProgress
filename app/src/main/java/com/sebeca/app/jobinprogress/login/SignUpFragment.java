package com.sebeca.app.jobinprogress.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sebeca.app.jobinprogress.R;
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
        mBinding.setViewModelSwitchServer(new SwitchServerViewModel());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onNameError(String msg) {
        mBinding.inputDataForm.inputName.setError(msg);
    }

    @Override
    public void onEmailError(String msg) {
        mBinding.inputDataForm.inputEmail.setError(msg);
    }

    @Override
    public void onPasswordError(String msg) {
        mBinding.inputDataForm.inputPassword.setError(msg);
    }

    @Override
    public void onActionProgress() {
        mBinding.signUpButton.setEnabled(false);
        showProgress(mBinding.signUpProgress, mBinding.signUpForm, true);
    }

    @Override
    public void onActionDone(boolean success) {
        mBinding.signUpButton.setEnabled(true);
        showProgress(mBinding.signUpProgress, mBinding.signUpForm, false);
        if (success) {
            FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_LOGIN, null);
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.error_sign_up_try_again),
                    Toast.LENGTH_LONG).show();
        }
    }
}
