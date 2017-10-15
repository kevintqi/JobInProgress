package com.sebeca.app.jobinprogress.main.joblist;

import android.databinding.ObservableField;

public final class Job {
    private final String mAddress;
    private final ObservableField<String> mStatus = new ObservableField<>();
    private final ObservableField<Integer> mIconId = new ObservableField<>();
    private final ObservableField<Long> mTargetStartTime = new ObservableField<>();
    private final ObservableField<Long> mTagetEndTime = new ObservableField<>();
    private final ObservableField<Long> mActualStartTime = new ObservableField<>();
    private ObservableField<Long> mActualEndTime = new ObservableField<>();

    public Job(String address) {
        mAddress = address;
    }

    public String getAddress() {return mAddress;}

    public ObservableField<String> getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus.set(status);
    }

    public ObservableField<Integer> getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId.set(iconId);
    }

    public ObservableField<Long> getTargetStartTime() {
        return mTargetStartTime;
    }

    public void setTargetStartTime(long value) {
        mTargetStartTime.set(value);
    }

    public ObservableField<Long> getTagetEndTime() {
        return mTagetEndTime;
    }

    public void setTagetEndTime(long value) {
        mTagetEndTime.set(value);
    }

    public ObservableField<Long> getActualStartTime() {
        return mActualStartTime;
    }

    public void setActualStartTime(long value) {
        mActualStartTime.set(value);
    }

    public ObservableField<Long> getActualEndTime() {
        return mActualEndTime;
    }

    public void setmActualEndTime(ObservableField<Long> mActualEndTime) {
        this.mActualEndTime = mActualEndTime;
    }

}
