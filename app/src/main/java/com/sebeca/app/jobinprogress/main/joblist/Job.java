package com.sebeca.app.jobinprogress.main.joblist;

import android.util.Log;

import com.sebeca.app.jobinprogress.database.JobEntity;

import org.json.JSONException;
import org.json.JSONObject;

public final class Job {
    public static final int BLOCKED = -1;
    public static final int NEW = 0;
    public static final int PROGRESSING = 1;
    public static final int FINISHING = 2;
    public static final int DONE = 3;
    private static final String TAG = Job.class.getSimpleName();
    private String mStatusText;
    private JobEntity mJobEntity = new JobEntity();

    public Job(JSONObject job, int priority) {
        try {
            mJobEntity.id = job.getString("_id");
            mJobEntity.priority = priority;
            mStatusText = job.getString("status");
            mapStatus(mStatusText);
            JSONObject location = job.getJSONObject("location");
            JSONObject address = location.getJSONObject("address");
            mJobEntity.address = address.getString("street") + "\n" +
                    address.getString("city") + "," +
                    address.getString("state") + " " +
                    address.getString("zipCode");
            JSONObject schedule = job.getJSONObject("actualSchedule");
            JSONObject time = schedule.getJSONObject("time");
            mJobEntity.targetStartTime = time.getString("start");
            mJobEntity.targetEndTime = time.getString("end");
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
    }

    public Job(JobEntity jobEntity) {
        mJobEntity = jobEntity;
        if (mJobEntity.status == NEW) {
            mStatusText = "New";
        } else if (mJobEntity.status == PROGRESSING) {
            mStatusText = "Progressing";
        } else if (mJobEntity.status == FINISHING) {
            mStatusText = "Finishing";
        } else if (mJobEntity.status == DONE) {
            mStatusText = "Done";
        } else if (mJobEntity.status == BLOCKED) {
            mStatusText = "Blocked";
        }
    }

    public JobEntity getJobEntity() {
        return mJobEntity;
    }

    public boolean isDone() {
        return mJobEntity.status == DONE;
    }

    public void updateStatus(int status) {
        if (status == BLOCKED || status == NEW) {
            mJobEntity.status = PROGRESSING;
            mStatusText = "Progressing";
        } else if (status == PROGRESSING) {
            mJobEntity.status = FINISHING;
            mStatusText = "Finishing";
        } else if (status == FINISHING) {
            mJobEntity.status = DONE;
            mStatusText = "Done";
        }
    }

    public String getId() {
        return mJobEntity.id;
    }

    public String getAddress() {
        return mJobEntity.address;
    }

    public int getStatus() {
        return mJobEntity.status;
    }

    public String getStatusText() {
        return mStatusText;
    }

    public int getIconId() {
        return 0;
    }

    public String getTargetStartTime() {
        return mJobEntity.targetStartTime;
    }

    public String getTargetEndTime() {
        return mJobEntity.targetEndTime;
    }

    public long getActualStartTime() {
        return mJobEntity.actualStartTime;
    }

    public long getActualEndTime() {
        return mJobEntity.actualEndTime;
    }

    public JSONObject toJSON() {
        try {
            JSONObject data = new JSONObject();
            data.put("status", mStatusText);
            JSONObject schedule = new JSONObject();
            JSONObject time = new JSONObject();
            time.put("start", mJobEntity.actualStartTime);
            time.put("end", mJobEntity.actualEndTime);
            schedule.put("time", time);
            data.put("actualSchedule", schedule);
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void mapStatus(String status) {
        if (status.equalsIgnoreCase("New")) {
            mJobEntity.status = NEW;
        } else if (status.equalsIgnoreCase("Starting") || status.equalsIgnoreCase("Progressing")) {
            mJobEntity.status = PROGRESSING;
        } else if (status.equalsIgnoreCase("Finishing")) {
            mJobEntity.status = FINISHING;
        } else if (status.equalsIgnoreCase("Done")) {
            mJobEntity.status = DONE;
        } else if (status.equalsIgnoreCase("Blocked")) {
            mJobEntity.status = BLOCKED;
        }
    }
}
