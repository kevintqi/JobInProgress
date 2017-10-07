package com.sebeca.app.jobinprogress.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentSignupBinding;


public class SignupFragment extends BaseFragment {
    private FragmentSignupBinding mBinding;

    @Override
    public boolean allowBack() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSignupBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    public void setFragmentSwitcher(FragmentSwitcher fragmentSwitcher) {

    }
}
