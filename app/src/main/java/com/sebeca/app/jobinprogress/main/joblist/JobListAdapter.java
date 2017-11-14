package com.sebeca.app.jobinprogress.main.joblist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.databinding.ItemJobListBinding;

import java.util.ArrayList;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    private final ArrayList<Job> mJobs;

    public JobListAdapter(ArrayList<Job> jobs) {
        mJobs =jobs;
    }

    @Override
    public JobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemJobListBinding binding = ItemJobListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JobListAdapter.ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(JobListAdapter.ViewHolder holder, int position) {
        Job job = mJobs.get(position);
        holder.bindJob(job);
    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements JobListItemViewModel.Listener {
        private final TextView mItemAddressText;
        private final ImageView mItemStatusIcon;
        private final TextView mItemStatusText;
        private final View mItemDetails;
        private final Button mItemActionButton;
        private JobListItemViewModel mViewModel;


        public ViewHolder(View view, ItemJobListBinding binding) {
            super(view);
            mViewModel = new JobListItemViewModel(this);
            binding.setViewModel(mViewModel);
            mItemAddressText = binding.itemAddressText;
            mItemStatusIcon = binding.itemStatusIcon;
            mItemStatusText = binding.itemStatusText;
            mItemDetails = binding.itemDetails;
            mItemActionButton = binding.itmActionButton;
        }

        @Override
        public void onCLickView() {
            if (mItemDetails.getVisibility() == View.GONE) {
                mItemDetails.setVisibility(View.VISIBLE);
            } else {
                mItemDetails.setVisibility(View.GONE);
            }
        }

        public void bindJob(Job job) {
            mItemAddressText.setText(job.getAddress());
            String status = job.getStatus();
            mItemStatusText.setText(status);
            if (status.equalsIgnoreCase("New") || status.equalsIgnoreCase("Blocked")) {
                mItemStatusIcon.setImageResource(R.mipmap.block);
                mItemActionButton.setText("Start");
            } else if (status.equalsIgnoreCase("Starting") || status.equalsIgnoreCase("Progressing")) {
                mItemStatusIcon.setImageResource(R.mipmap.progress);
                mItemActionButton.setText("Finishing");
            } else if (status.equalsIgnoreCase("Finishing")) {
                mItemStatusIcon.setImageResource(R.mipmap.finishing);
                mItemActionButton.setText("Done");
            } else if (status.equalsIgnoreCase("Done")) {
                mItemStatusIcon.setImageResource(R.mipmap.check);
                mItemActionButton.setText("Done");
                mItemActionButton.setEnabled(false);
            }
        }
    }

}
