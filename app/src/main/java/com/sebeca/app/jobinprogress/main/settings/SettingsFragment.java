package com.sebeca.app.jobinprogress.main.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentSettingsBinding;


/**
 * Created by kevinqi on 11/4/17.
 */

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding mBinding;
    private SettingsViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        mViewModel = new SettingsViewModel(getActivity());
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
