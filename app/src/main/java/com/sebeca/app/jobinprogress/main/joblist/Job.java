package com.sebeca.app.jobinprogress.main.joblist;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public final class Job {
    private static final String TAG = Job.class.getSimpleName();
    private String mId;
    private String mAddress;
    private String mStatus;
    private String mTargetStartTime;
    private String mTargetEndTime;
    private long mActualStartTime;
    private long mActualEndTime;

    public Job(JSONObject job) {
        try {
            mId = job.getString("_id");
            mStatus = job.getString("status");
            JSONObject location = job.getJSONObject("location");
            JSONObject address = location.getJSONObject("address");
            mAddress = address.getString("street") + "\n" +
                    address.getString("city") + "," +
                    address.getString("state") + " " +
                    address.getString("zipCode");
            JSONObject schedule = job.getJSONObject("actualSchedule");
            JSONObject time = schedule.getJSONObject("time");
            mTargetStartTime = time.getString("start");
            mTargetEndTime = time.getString("end");
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
    }

    public String getId() {
        return mId;
    }

    public String getAddress() {return mAddress;}

    public String getStatus() {
        return mStatus;
    }

    public int getIconId() {
        return 0;
    }

    public String getTargetStartTime() {
        return mTargetStartTime;
    }

    public String getTargetEndTime() {
        return mTargetEndTime;
    }

    public long getActualStartTime() {
        return mActualStartTime;
    }

    public long getActualEndTime() {
        return mActualEndTime;
    }
}
