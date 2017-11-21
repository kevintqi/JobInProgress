package com.sebeca.app.jobinprogress.main.joblist;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.main.MainService;

import javax.inject.Inject;

/**
 * Created by kevinqi on 11/5/17.
 */

public class JobListItemViewModel extends ViewModel {
    private static final String TAG = JobListItemViewModel.class.getSimpleName();
    @Inject
    ActiveJobDataStore mActiveJobDataStore;
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

    public void onClickView(View v) {
        if (mJob.isActive()) {
            mListener.onCLickView();
        }
    }

    public void onClickAction(View v) {
        Log.i(TAG, "ActiveJobId: " + mJob.getId());
        mActiveJobDataStore.put(mJob.getId());
        startService();
        mJob.updateStatus();
        mListener.onClickAction(mJob);
    }

    private void startService() {
        Intent intent = new Intent(mContext, MainService.class);
        intent.putExtra(MainService.ACTION_KEY, MainService.ACTION_START_LOCATION_REPORT);
        mContext.startService(intent);
    }

    public interface Listener {
        void onCLickView();

        void onClickAction(Job job);
    }
}
