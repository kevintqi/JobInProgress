package com.sebeca.app.jobinprogress.main.joblist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sebeca.app.jobinprogress.di.App;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Job List ViewModel
 */

public class JobListViewModel extends AndroidViewModel {

    @Inject
    JobListRepository mJobListRepository;

    public JobListViewModel(@NonNull Application app) {
        super(app);
        ((App) app).getAppComponent().inject(this);
    }

    LiveData<ArrayList<Job>> getJobList() {
        return mJobListRepository.getJobList();
    }
}
