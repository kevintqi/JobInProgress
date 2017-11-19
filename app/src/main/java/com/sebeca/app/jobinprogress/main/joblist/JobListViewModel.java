package com.sebeca.app.jobinprogress.main.joblist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by kevinqi on 11/18/17.
 */

public class JobListViewModel extends AndroidViewModel {
    private final JobListRepository mJobListRepository;

    public JobListViewModel(@NonNull Application app) {
        super(app);
        mJobListRepository = new JobListRepository(app);
    }

    public LiveData<ArrayList<Job>> getJobList() {
        return mJobListRepository.getJobList();
    }
}
