package com.sebeca.app.jobinprogress.main.joblist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

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

    private final JobDao mJobDao;
    private final MutableLiveData<ArrayList<Job>> mJobList = new MutableLiveData<>();

    public JobListRepository(AppDatabase database) {
        super(TAG);
        mJobDao = database.jobDao();
    }

    LiveData<ArrayList<Job>> getJobList() {
        run(new LoadJobListTask());
        return mJobList;
    }

    void requestUpdateJobList(ArrayList<Job> jobList) {
        run(new UpdateJobListTask(jobList));
        mJobList.setValue(jobList);
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

    private class LoadJobListTask implements Runnable {
        @Override
        public void run() {
            ArrayList<Job> jobs = new ArrayList<>();
            JobEntity[] jobEntities = mJobDao.loadAllJobs();
            for (JobEntity jobEntity : jobEntities) {
                Job job = new Job(jobEntity);
                jobs.add(job);
            }
            mJobList.postValue(jobs);
        }
    }
}
