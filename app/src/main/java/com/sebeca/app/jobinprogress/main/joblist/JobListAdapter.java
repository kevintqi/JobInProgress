package com.sebeca.app.jobinprogress.main.joblist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebeca.app.jobinprogress.databinding.ItemJobListBinding;

import java.util.ArrayList;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    private final Context mContext;
    private ArrayList<Job> mJobs = new ArrayList<>();

    public JobListAdapter(Context context) {
        mContext = context;
    }

    public void setJobList(ArrayList<Job> jobs) {
        mJobs = jobs;
        notifyDataSetChanged();
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
        private final JobListItemViewModel mViewModel;
        private final TextView mItemAddressText;
        private final ImageView mItemStatusIcon;
        private final View mItemDetails;
        private final Button mItemActionButton;
        private final Button mItemPauseButton;
        private final TextView mItemStartTimeText;
        private final TextView mItemDurationText;
        private final View mItemJobAction;

        public ViewHolder(Context context, View view, ItemJobListBinding binding) {
            super(view);
            mViewModel = new JobListItemViewModel(context, this);
            binding.setViewModel(mViewModel);
            mItemAddressText = binding.itemJobBrief.itemAddressText;
            mItemStatusIcon = binding.itemJobBrief.itemStatusIcon;
            mItemDetails = binding.itemDetails;
            mItemJobAction = binding.itemJobAction.getRoot();
            mItemActionButton = binding.itemJobAction.itmActionButton;
            mItemPauseButton = binding.itemJobAction.itemPauseButton;
            mItemStartTimeText = binding.itemJobTime.itemStartTimeText;
            mItemDurationText = binding.itemJobTime.itemDurationText;
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
        public void onUpdateJob(Job job) {
            updateStatus(job);
        }

        public void bindJob(Job job) {
            mViewModel.setJob(job);
            mItemAddressText.setText(job.getAddress());
            updateStatus(job);
        }

        private void updateStatus(Job job) {
            mItemActionButton.setEnabled(true);
            mItemStatusIcon.setImageResource(job.getStatusIconId());
            mItemStartTimeText.setText(job.getStartTimeText());
            mItemDurationText.setText(job.getDurationText());
            mItemActionButton.setText(job.getActionText());
            mItemActionButton.setEnabled(true);
            mItemPauseButton.setEnabled(true);
            mItemJobAction.setVisibility(View.VISIBLE);
            if (job.isNew()) {
                mItemPauseButton.setEnabled(false);
            } else if (job.isBlocked()) {
                mItemPauseButton.setEnabled(false);
                mItemDurationText.setText(job.getStatusText());
            } else if (job.isDone()) {
                mItemJobAction.setVisibility(View.GONE);
            }
        }
    }

}
