package com.sebeca.app.jobinprogress.main.joblist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sebeca.app.jobinprogress.R;

import java.util.ArrayList;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    private final ArrayList<Job> mJobs;

    public JobListAdapter(ArrayList<Job> jobs) {
        mJobs =jobs;
    }

    @Override
    public JobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_list, parent, false);
        return new JobListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobListAdapter.ViewHolder holder, int position) {
        Job job = mJobs.get(position);
        holder.bindJob(job.getAddress());
    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.itemTitle);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

        public void bindJob(String title) {
            mTextView.setText(title);
        }
    }

}
