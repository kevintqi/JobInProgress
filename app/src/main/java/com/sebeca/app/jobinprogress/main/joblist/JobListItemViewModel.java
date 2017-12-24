package com.sebeca.app.jobinprogress.main.joblist;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.locator.LocationRepository;
import com.sebeca.app.jobinprogress.main.MainService;
import com.sebeca.app.jobinprogress.main.map.JobMarker;
import com.sebeca.app.jobinprogress.main.map.JobMarkerRepository;
import com.sebeca.app.jobinprogress.util.ElapsedTimer;

import javax.inject.Inject;


public class JobListItemViewModel extends ViewModel implements ElapsedTimer.Callback {
    @Inject
    Application mApp;
    @Inject
    ActiveJobDataStore mActiveJobDataStore;
    @Inject
    JobListRepository mJobListRepository;
    @Inject
    JobMarkerRepository mJobMarkerRepository;
    @Inject
    LocationRepository mLocationRepository;

    private static final String TAG = JobListItemViewModel.class.getSimpleName();
    private static final int UPDATE_INTERVAL = 1000;
    private final Listener mListener;
    private final ElapsedTimer mElapsedTimer;
    private Job mJob;
    private ElapsedTimer.State mTimerState;

    public JobListItemViewModel(Context context, Listener listener) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        mListener = listener;
        mElapsedTimer = new ElapsedTimer(UPDATE_INTERVAL, this);
    }

    public void setJob(Job job) {
        mJob = job;
        if (!mJob.isNew() && !mJob.isDone()) {
            mElapsedTimer.start();
        }
    }

    public void onClickView() {
        if (shouldShowDetails()) {
            mListener.onCLickView();
        }
    }

    private boolean isActiveJob() {
        return mJob.getId().equals(mActiveJobDataStore.get());
    }

    private boolean shouldShowDetails() {
        return isActiveJob() || mJob.isDone();
    }

    public void onClickAction() {
        advanceJobStatus();
        mListener.onUpdateJob(mJob);
    }

    private void advanceJobStatus() {
        int status = mJob.getStatus();
        if (status == Job.NEW) {
            mJob.setStatus(Job.PROGRESSING);
            mElapsedTimer.start();
            mJob.setStartTime(mTimerState.startTime);
        } else if (status == Job.PROGRESSING) {
            mJob.setStatus(Job.DONE);
            mElapsedTimer.stop();
        } else if (status == Job.BLOCKED) {
            mElapsedTimer.resume();
            mJob.setTotalDuration(mTimerState.totalDuration);
            mJob.setTotalBlockedDuration(mTimerState.totalPausedDuration);
            mJob.setStatus(Job.PROGRESSING);
        }
        startLocationReport();
        mJobListRepository.requestUpdateJob(mJob);
        addJobMarker();
    }

    private void startLocationReport() {
        Intent intent = new Intent(mApp, MainService.class);
        intent.putExtra(MainService.ACTION_KEY, MainService.ACTION_START_LOCATION_REPORT);
        mApp.startService(intent);
    }

    private void addJobMarker() {
        Location location = mLocationRepository.getLastLocation();
        if (location != null) {
            JobMarker jobMarker = new JobMarker(mJob.getId(), mJob.getStatusText(), location);
            mJobMarkerRepository.addJobMarker(jobMarker);
        }
    }

    public void onClickBlock() {
        mJob.setStatus(Job.BLOCKED);
        mElapsedTimer.pause();
        mJobListRepository.requestUpdateJob(mJob);
        addJobMarker();
        mListener.onUpdateJob(mJob);
    }

    @Override
    public void onUpdate(ElapsedTimer.State state) {
        mTimerState = state;
    }

    @Override
    public void onTick() {
        mJob.setTotalDuration(mTimerState.totalDuration);
        mJob.setTotalBlockedDuration(mTimerState.totalPausedDuration);
        mListener.onUpdateJob(mJob);
    }

    public interface Listener {
        void onCLickView();

        void onUpdateJob(Job job);
    }

}
