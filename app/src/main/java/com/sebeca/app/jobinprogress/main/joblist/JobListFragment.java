package com.sebeca.app.jobinprogress.main.joblist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentJobListBinding;


public class JobListFragment extends Fragment{
    private FragmentJobListBinding mBinding;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private JobListProvider mJobListProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentJobListBinding.inflate(inflater, container,false);
        mRecyclerView = mBinding.recyclerView;
        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mJobListProvider = new JobListProvider(this.getContext());
        mRecyclerView.setAdapter(mJobListProvider.getJobListAdapter());
        return mBinding.getRoot();
    }

    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mJobListProvider.init();
    }
}
