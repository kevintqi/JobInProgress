package com.sebeca.app.jobinprogress.main.joblist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;
import com.sebeca.app.jobinprogress.data.SessionCookieDataStore;
import com.sebeca.app.jobinprogress.database.AppDatabase;
import com.sebeca.app.jobinprogress.database.JobDao;
import com.sebeca.app.jobinprogress.database.JobEntity;
import com.sebeca.app.jobinprogress.login.LoginActivity;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Job List Data Repository
 */

public class JobListRepository {
    private static final String TAG = JobListRepository.class.getSimpleName();

    private final Context mContext;
    private final JobDao mJobDao;
    private final Handler mHandler;
    private final MutableLiveData<ArrayList<Job>> mJobList = new MutableLiveData<>();
    private final MyObjectRequest.Callback mCallback = new MyObjectRequest.Callback() {

        @Override
        public void onSuccess(JSONObject response) {
            try {
                JSONArray jobList = response.getJSONArray("jobs");
                refreshJobList(jobList);
            } catch (JSONException e) {
                Log.wtf(TAG, e);
            }
        }

        @Override
        public void onError(VolleyError error) {
            if (error instanceof AuthFailureError) {
                SessionCookieDataStore dataStore = new SessionCookieDataStore(mContext);
                dataStore.clear();
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        }
    };

    public JobListRepository(Application application, AppDatabase database) {
        mContext = application;
        mJobDao = database.jobDao();
        HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        mHandler = new Handler(thread.getLooper());
    }

    LiveData<ArrayList<Job>> getJobList() {
        requestJobList();
        mHandler.post(new LoadJobTask());
        return mJobList;
    }

    private void requestJobList() {
        ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
        final String url = dataStore.get() + mContext.getString(R.string.url_jobs);
        Log.i(TAG, "URL: " + url);
        MyObjectRequest request = new MyObjectRequest(mContext, url, Request.Method.GET, mCallback);
        request.send(null);
    }

    private void refreshJobList(JSONArray jobList) {
        if (jobList != null) {
            ArrayList<Job> jobs = new ArrayList<>();
            boolean activeJobSet = false;
            for (int i = 0; i < jobList.length(); ++i) {
                Job job = newJob(jobList, i);
                if (job != null) {
                    if (!activeJobSet && !job.isDone()) {
                        job.setActive(true);
                        activeJobSet = true;
                    }
                    jobs.add(job);
                }
            }
            mHandler.post(new InsertJobTask(jobs));
        }
    }

    private Job newJob(JSONArray jobList, int index) {
        try {
            JSONObject data = jobList.getJSONObject(index);
            return new Job(data, index);
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
        return null;
    }

    private class InsertJobTask implements Runnable {
        private final ArrayList<Job> mJobList;

        InsertJobTask(ArrayList<Job> jobList) {
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

    private class LoadJobTask implements Runnable {
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
