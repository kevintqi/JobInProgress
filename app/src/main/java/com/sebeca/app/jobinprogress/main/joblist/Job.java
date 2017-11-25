package com.sebeca.app.jobinprogress.main.joblist;

import android.util.Log;

import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.database.JobEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Job {
    public static final int BLOCKED = -1;
    public static final int NEW = 0;
    public static final int PROGRESSING = 1;
    public static final int DONE = 2;

    private static final String TAG = Job.class.getSimpleName();
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private String mStatusText;
    private String mStartTimeText;
    private int mStatusIconId;
    private String mActionText;
    private int mPreviousTotalDuration = 0;
    private int mPreviousTotalBlockedDuration = 0;
    private JobEntity mJobEntity = new JobEntity();

    public Job(JSONObject job, int priority) {
        try {
            mJobEntity.id = job.getString("_id");
            mJobEntity.priority = priority;
            mStatusText = job.getString("status");
            setStatus(toStatus(mStatusText));
            JSONObject location = job.getJSONObject("location");
            JSONObject address = location.getJSONObject("address");
            mJobEntity.address = address.getString("street") + "\n" +
                    address.getString("city") + ", " +
                    address.getString("state") + " " +
                    address.getString("zipCode");
            JSONObject schedule = job.getJSONObject("actualSchedule");
            JSONObject time = schedule.getJSONObject("time");
            mStartTimeText = time.getString("start");
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
    }

    public Job(JobEntity jobEntity) {
        mJobEntity = jobEntity;
        mPreviousTotalDuration = jobEntity.totalDuration;
        mPreviousTotalBlockedDuration = jobEntity.totalBlockedDuration;
        setStatus(mJobEntity.status);
        setStartTime(jobEntity.startTime);
    }

    private int toStatus(String statusText) {
        int status = NEW;
        if (statusText.equalsIgnoreCase("New")) {
            status = NEW;
        } else if (statusText.equalsIgnoreCase("Starting") ||
                statusText.equalsIgnoreCase("Progressing") ||
                statusText.equalsIgnoreCase("Finishing")) {
            status = PROGRESSING;
        } else if (statusText.equalsIgnoreCase("Done")) {
            status = DONE;
        } else if (statusText.equalsIgnoreCase("Blocked")) {
            status = BLOCKED;
        }
        return status;
    }

    public JobEntity getJobEntity() {
        return mJobEntity;
    }

    public boolean isNew() {
        return mJobEntity.status == NEW;
    }

    public boolean isDone() {
        return mJobEntity.status == DONE;
    }

    public boolean isBlocked() {
        return mJobEntity.status == BLOCKED;
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

    public void setStatus(int status) {
        mJobEntity.status = status;
        if (mJobEntity.status == NEW) {
            mStatusText = "New";
            mActionText = "Start";
            mStatusIconId = R.mipmap.start;
            mJobEntity.startTime = 0L;
            mJobEntity.totalDuration = 0;
            mJobEntity.totalBlockedDuration = 0;
        } else if (mJobEntity.status == PROGRESSING) {
            mStatusText = "Progressing";
            mActionText = "Done";
            mStatusIconId = R.mipmap.progress;
        } else if (mJobEntity.status == DONE) {
            mStatusText = "Done";
            mActionText = "Done";
            mStatusIconId = R.mipmap.check;
        } else if (mJobEntity.status == BLOCKED) {
            mStatusText = "Blocked";
            mActionText = "Resume";
            mStatusIconId = R.mipmap.block;
        }
    }

    public String getStatusText() {
        return mStatusText;
    }

    public void setStartTime(long startTime) {
        if (mJobEntity.startTime == 0L) {
            mJobEntity.startTime = startTime;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        mStartTimeText = "Started:  " + dateFormat.format(mJobEntity.startTime);
    }

    public void setTotalDuration(int duration) {
        mJobEntity.totalDuration = duration + mPreviousTotalDuration;
    }

    public void setTotalBlockedDuration(int duration) {
        mJobEntity.totalBlockedDuration = duration + mPreviousTotalBlockedDuration;
    }

    public String getStartTimeText() {
        return mStartTimeText;
    }

    public String getDurationText() {
        int duration = mJobEntity.totalDuration - mJobEntity.totalBlockedDuration;
        int hour = duration / HOUR;
        int left = duration % HOUR;
        int minutes = left / MINUTE;
        int seconds = (left % MINUTE) / SECOND;
        return String.format(Locale.ENGLISH, "%d:%02d:%02d", hour, minutes, seconds);
    }

    public String getActionText() {
        return mActionText;
    }

    public int getStatusIconId() {
        return mStatusIconId;
    }

    public JSONObject toJSON() {
        try {
            JSONObject data = new JSONObject();
            data.put("status", mStatusText);
            JSONObject schedule = new JSONObject();
            JSONObject time = new JSONObject();
            time.put("start", mJobEntity.startTime);
            time.put("totalDuration", mJobEntity.totalDuration);
            time.put("totalBlocked", mJobEntity.totalBlockedDuration);
            schedule.put("time", time);
            data.put("actualSchedule", schedule);
            data.put("lastUpdateTime", mJobEntity.lastUpdateTime);
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "[" + mJobEntity.priority + "]" +
                mJobEntity.id + ":" + mStatusText +
                "(" + mJobEntity.status + ")" +
                "start=" + mJobEntity.startTime + "," +
                "duration=" + mJobEntity.totalDuration + "," +
                "blocked=" + mJobEntity.totalBlockedDuration;
    }
}
