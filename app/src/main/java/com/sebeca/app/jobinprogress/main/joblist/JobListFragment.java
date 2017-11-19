package com.sebeca.app.jobinprogress.main.joblist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebeca.app.jobinprogress.databinding.FragmentJobListBinding;

import java.util.ArrayList;


public class JobListFragment extends Fragment{
    private FragmentJobListBinding mBinding;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private JobListAdapter mJobListAdapter;
    private JobListViewModel mJobListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentJobListBinding.inflate(inflater, container, false);
        mRecyclerView = mBinding.recyclerView;
        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mJobListAdapter = new JobListAdapter(this.getContext());
        mRecyclerView.setAdapter(mJobListAdapter);

        mJobListViewModel = ViewModelProviders.of(this).get(JobListViewModel.class);
        final Observer<ArrayList<Job>> jobListObserver = new Observer<ArrayList<Job>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Job> jobs) {
                mJobListAdapter.setJobList(jobs);
            }
        };
        mJobListViewModel.getJobList().observe(this, jobListObserver);

        return mBinding.getRoot();
    }

}
