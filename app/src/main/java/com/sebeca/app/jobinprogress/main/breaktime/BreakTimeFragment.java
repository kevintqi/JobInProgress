package com.sebeca.app.jobinprogress.main.breaktime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentBreakTimeBinding;

public class BreakTimeFragment extends Fragment {
    private FragmentBreakTimeBinding mBinding;
    private BreakTimeViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentBreakTimeBinding.inflate(inflater, container, false);
        mViewModel = new BreakTimeViewModel(getActivity());
        mBinding.setViewModel(mViewModel);
        mBinding.setDataModel(mViewModel.getDataModel());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
