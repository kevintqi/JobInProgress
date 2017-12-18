package com.sebeca.app.jobinprogress.main.map;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.sebeca.app.jobinprogress.database.AppDatabase;
import com.sebeca.app.jobinprogress.database.DataTaskRunner;
import com.sebeca.app.jobinprogress.database.JobMarkerDao;
import com.sebeca.app.jobinprogress.database.JobMarkerEntity;

import java.util.ArrayList;


public class JobMarkerRepository extends DataTaskRunner {
    private static final String TAG = JobMarkerRepository.class.getSimpleName();

    private final JobMarkerDao mJobMarkerDao;
    private final MutableLiveData<ArrayList<JobMarker>> mJobMakers = new MutableLiveData<>();

    public JobMarkerRepository(AppDatabase database) {
        super(TAG);
        mJobMarkerDao = database.jobMarkerDao();
    }

    public LiveData<ArrayList<JobMarker>> getJobMakers(String jobId) {
        run(new LoadJobMarkersTask(jobId));
        return mJobMakers;
    }

    public void addJobMarker(JobMarker jobMarker) {
        run(new InsertJobMarkersTask(jobMarker));
        run(new LoadJobMarkersTask(jobMarker.getJobId()));
    }

    private class LoadJobMarkersTask implements Runnable {
        private final String mJobId;

        LoadJobMarkersTask(String jobId) {
            mJobId = jobId;
        }

        @Override
        public void run() {
            JobMarkerEntity[] jobMarkerEntities = mJobMarkerDao.loadJobMakers(mJobId);
            ArrayList<JobMarker> jobMarkers = new ArrayList<>();
            for (JobMarkerEntity item : jobMarkerEntities) {
                jobMarkers.add(new JobMarker(item));
            }
            Log.i(TAG, "numJobMarkers=" + jobMarkers.size());
            mJobMakers.postValue(jobMarkers);
        }
    }

    private class InsertJobMarkersTask implements Runnable {
        private final JobMarker mJobMaker;

        InsertJobMarkersTask(JobMarker jobMarker) {
            mJobMaker = jobMarker;
        }

        @Override
        public void run() {
            mJobMarkerDao.insertJobMarks(mJobMaker.getJobMarkerEntity());
        }
    }
}
