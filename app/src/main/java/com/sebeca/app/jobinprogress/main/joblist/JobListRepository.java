package com.sebeca.app.jobinprogress.main.joblist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.sebeca.app.jobinprogress.data.ActiveJobDataStore;
import com.sebeca.app.jobinprogress.database.AppDatabase;
import com.sebeca.app.jobinprogress.database.DataTaskRunner;
import com.sebeca.app.jobinprogress.database.JobDao;
import com.sebeca.app.jobinprogress.database.JobEntity;

import java.util.ArrayList;

/**
 * Job List Data Repository
 */

public class JobListRepository extends DataTaskRunner {
    private static final String TAG = JobListRepository.class.getSimpleName();

    private final Application mApp;
    private final JobDao mJobDao;
    private final ActiveJobDataStore mActiveJobDataStore;
    private final MutableLiveData<ArrayList<Job>> mJobList = new MutableLiveData<>();

    public JobListRepository(Application application, AppDatabase database, ActiveJobDataStore activeJobDataStore) {
        super(TAG);
        mApp = application;
        mJobDao = database.jobDao();
        mActiveJobDataStore = activeJobDataStore;
    }

    LiveData<ArrayList<Job>> getJobList() {
        run(new LoadJobListTask());
        return mJobList;
    }

    void requestUpdateJobList(ArrayList<Job> jobList) {
        run(new UpdateJobListTask(jobList));
        run(new LoadJobListTask());
    }

    void requestUpdateJob(Job job) {
        run(new UpdateJobTask(job));
        if (job.isDone()) {
            run(new LoadJobListTask());
        }
        JobReporter jobReporter = new JobReporter(mApp);
        jobReporter.sendRequest(job);
    }

    private class LoadJobListTask implements Runnable {
        @Override
        public void run() {
            boolean activeJobSet = false;
            ArrayList<Job> jobs = new ArrayList<>();
            JobEntity[] jobEntities = mJobDao.loadAllJobs();
            for (JobEntity jobEntity : jobEntities) {
                Job job = new Job(jobEntity);
                if (!activeJobSet && !job.isDone()) {
                    activeJobSet = true;
                    Log.i(TAG, "ActiveJobId: " + job.getId());
                    mActiveJobDataStore.put(job.getId());
                }
                jobs.add(job);
            }
            mJobList.postValue(jobs);
        }
    }

    private class UpdateJobListTask implements Runnable {
        private final ArrayList<Job> mJobList;

        UpdateJobListTask(ArrayList<Job> jobList) {
            mJobList = jobList;
        }

        @Override
        public void run() {
            mJobDao.clearAllJobs();
            for (Job job : mJobList) {
                mJobDao.insertJobs(job.getJobEntity());
            }
        }
    }

    private class UpdateJobTask implements Runnable {
        private final Job mJob;

        UpdateJobTask(Job job) {
            mJob = job;
        }

        @Override
        public void run() {
            mJobDao.insertJobs(mJob.getJobEntity());
        }
    }
}
