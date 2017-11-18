package com.sebeca.app.jobinprogress.main.joblist;

import android.content.Context;
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
    private final Context mContext;
    private final ArrayList<Job> mJobs;

    public JobListAdapter(Context context, ArrayList<Job> jobs) {
        mContext = context;
        mJobs =jobs;
    }

    @Override
    public JobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemJobListBinding binding = ItemJobListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JobListAdapter.ViewHolder(mContext, binding.getRoot(), binding);
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


        public ViewHolder(Context context, View view, ItemJobListBinding binding) {
            super(view);
            mViewModel = new JobListItemViewModel(context, this);
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

        @Override
        public void onClickAction(Job job) {
            updateStatus(job);
        }

        public void bindJob(Job job) {
            mViewModel.setJob(job);
            mItemAddressText.setText(job.getAddress());
            updateStatus(job);
        }

        private void updateStatus(Job job) {
            int status = job.getStatus();
            mItemStatusText.setText(job.getStatusText());
            if (status == Job.NEW || status == Job.BLOCKED) {
                mItemStatusIcon.setImageResource(R.mipmap.block);
                mItemActionButton.setText("Start");
            } else if (status == Job.PROGRESSING) {
                mItemStatusIcon.setImageResource(R.mipmap.progress);
                mItemActionButton.setText("Finishing");
            } else if (status == Job.FINISHING) {
                mItemStatusIcon.setImageResource(R.mipmap.finishing);
                mItemActionButton.setText("Done");
            } else if (status == Job.DONE) {
                mItemStatusIcon.setImageResource(R.mipmap.check);
                mItemActionButton.setText("Done");
                mItemActionButton.setEnabled(false);
            }
        }
    }

}
