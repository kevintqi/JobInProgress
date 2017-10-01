package com.sebeca.app.jobinprogress.breaktime;

import android.databinding.ObservableField;

public class BreakTimeModel {
    private ObservableField<String> mStartButtonText = new ObservableField<>();
    private ObservableField<String> mStatusText = new ObservableField<>();
    private ObservableField<Boolean> mRegularTextSize = new ObservableField<>();

    public ObservableField<String> getStartButtonText() {
        return mStartButtonText;
    }

    void setStartButtonText(String v) {
        mStartButtonText.set(v);
    }

    void setStatusText(String v, boolean useRegularSize) {
        mStatusText.set(v);
        mRegularTextSize.set(useRegularSize);
    }

    public ObservableField<String> getStatusText() {
        return mStatusText;
    }

    public ObservableField<Boolean> getRegularTextSize() {
        return mRegularTextSize;
    }
}
