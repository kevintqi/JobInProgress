package com.sebeca.app.jobinprogress.main.joblist;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;

import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.main.MainService;

import javax.inject.Inject;


public class JobListItemViewModel extends ViewModel {
    private static final String TAG = JobListItemViewModel.class.getSimpleName();
    @Inject
    ActiveJobDataStore mActiveJobDataStore;

    @Inject
    JobListRepository mJobListRepository;
    private Context mContext;
    private Listener mListener;
    private Job mJob;
    public JobListItemViewModel(Context context, Listener listener) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        mContext = context;
        mListener = listener;
    }

    public void setJob(Job job) {
        mJob = job;
    }

    public void onClickView() {
        if (shouldShowDetails()) {
            mListener.onCLickView();
        }
    }

    private boolean shouldShowDetails() {
        return mJob.getId().equals(mActiveJobDataStore.get()) || mJob.isDone();
    }

    public void onClickAction() {
        updateJobStatus();
        mListener.onClickAction(mJob);
    }

    private void updateJobStatus() {
        int status = mJob.getStatus();
        if (status == Job.NEW || status == Job.BLOCKED) {
            mJob.setStartTime(System.currentTimeMillis());
        }
        startLocationReport();
        mJob.updateStatus(mJob.getStatus());
        mJobListRepository.requestUpdateJob(mJob);
    }

    private void startLocationReport() {
        Intent intent = new Intent(mContext, MainService.class);
        intent.putExtra(MainService.ACTION_KEY, MainService.ACTION_START_LOCATION_REPORT);
        mContext.startService(intent);
    }

    public interface Listener {
        void onCLickView();

        void onClickAction(Job job);
    }
}
