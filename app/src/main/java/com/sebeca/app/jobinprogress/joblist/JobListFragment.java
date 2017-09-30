package com.sebeca.app.jobinprogress.joblist;

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
    private final ArrayList<Job> mJobData = new ArrayList<>();
    private FragmentJobListBinding mBinding;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private JobListAdapter mJobListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentJobListBinding.inflate(inflater, container,false);
        mRecyclerView = mBinding.recyclerView;
        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mJobListAdapter = new JobListAdapter(mJobData);
        mRecyclerView.setAdapter(mJobListAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void  onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mJobData.add(new Job("Line a"));
        mJobData.add(new Job("Line b"));
        mJobData.add(new Job("Line c"));
        mJobData.add(new Job("Line d"));
    }
}
