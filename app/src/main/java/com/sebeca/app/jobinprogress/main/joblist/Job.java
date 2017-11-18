package com.sebeca.app.jobinprogress.main.joblist;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public final class Job {
    public static final int BLOCKED = -1;
    public static final int NEW = 0;
    public static final int PROGRESSING = 1;
    public static final int FINISHING = 2;
    public static final int DONE = 3;
    private static final String TAG = Job.class.getSimpleName();
    private String mId;
    private String mAddress;
    private String mStatusText;
    private String mTargetStartTime;
    private String mTargetEndTime;
    private long mActualStartTime;
    private long mActualEndTime;
    private int mStatus = NEW;
    private boolean mActive = false;

    public Job(JSONObject job) {
        try {
            mId = job.getString("_id");
            mStatusText = job.getString("status");
            mapStatus(mStatusText);
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
            if (isDone()) {
                mActive = true;
            }
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    public boolean isDone() {
        return mStatus == DONE;
    }

    public void updateStatus() {
        if (mStatus == BLOCKED || mStatus == NEW) {
            mStatus = PROGRESSING;
            mStatusText = "Progressing";
        } else if (mStatus == PROGRESSING) {
            mStatus = FINISHING;
            mStatusText = "Finishing";
        } else if (mStatus == FINISHING) {
            mStatus = DONE;
            mStatusText = "Done";
        }
    }

    public String getId() {
        return mId;
    }

    public String getAddress() {return mAddress;}

    public int getStatus() {
        return mStatus;
    }

    public String getStatusText() {
        return mStatusText;
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

    private void mapStatus(String status) {
        if (status.equalsIgnoreCase("New")) {
            mStatus = NEW;
        } else if (status.equalsIgnoreCase("Starting") || status.equalsIgnoreCase("Progressing")) {
            mStatus = PROGRESSING;
        } else if (status.equalsIgnoreCase("Finishing")) {
            mStatus = FINISHING;
        } else if (status.equalsIgnoreCase("Done")) {
            mStatus = DONE;
        } else if (status.equalsIgnoreCase("Blocked")) {
            mStatus = BLOCKED;
        }
    }
}
