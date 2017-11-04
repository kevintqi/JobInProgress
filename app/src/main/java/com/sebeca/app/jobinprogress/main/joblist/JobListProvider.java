package com.sebeca.app.jobinprogress.main.joblist;

import android.content.Context;

import com.sebeca.app.jobinprogress.data.JobListDataStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevinqi on 10/29/17.
 */

public class JobListProvider implements JobListDataStore.Callback {
    private final ArrayList<Job> mJobData = new ArrayList<>();
    JobListDataStore mJobListDataStore;
    private Context mContext;
    private JobListAdapter mJobListAdapter;

    public JobListProvider(Context ctx) {
        mContext = ctx;
        mJobListAdapter = new JobListAdapter(mJobData);
        mJobListDataStore = new JobListDataStore(mContext);
    }

    public JobListAdapter getJobListAdapter() {
        return mJobListAdapter;
    }

    public void init() {
        JSONArray jobList = mJobListDataStore.get();
        onUpdate(jobList);
    }

    @Override
    public void onUpdate(JSONArray jobList) {
        mJobData.clear();
        try {
            if (jobList != null) {
                for (int i = 0; i < jobList.length(); ++i) {
                    JSONObject jobInJson = jobList.getJSONObject(i);
                    mJobData.add(new Job(jobInJson));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJobListAdapter.notifyDataSetChanged();
    }
}
