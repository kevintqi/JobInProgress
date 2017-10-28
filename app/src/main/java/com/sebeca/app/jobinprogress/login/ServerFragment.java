package com.sebeca.app.jobinprogress.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentServerBinding;

/**
 * Created by kevinqi on 10/28/17.
 */

public class ServerFragment extends BaseFragment {
    private FragmentServerBinding mBinding;
    private ServerViewModel mViewModel;

    @Override
    public boolean allowBack() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentServerBinding.inflate(inflater, container, false);
        mViewModel = new ServerViewModel(getContext());
        mBinding.setViewModel(mViewModel);
        mBinding.setDataModel(mViewModel.getDataModel());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
