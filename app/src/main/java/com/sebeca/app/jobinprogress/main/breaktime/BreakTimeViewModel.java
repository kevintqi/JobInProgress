package com.sebeca.app.jobinprogress.main.breaktime;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.main.MainService;

import java.util.Locale;

public class BreakTimeViewModel {
    private static final String TAG = BreakTimeViewModel.class.getSimpleName();
    private static final long SECOND = 1000;
    private static final long BLOCK = 30 * 60 * SECOND;
    private Context mContext;
    private long mBreakTime = 0L;
    private boolean mStarted = false;
    private BreakTimeModel mDataModel = new BreakTimeModel();

    private CountDownTimer mCountDownTimer;

    BreakTimeViewModel(Context context) {
        mContext = context;
        mDataModel.setStartButtonText(context.getString(R.string.button_text_start));
        mDataModel.setStatusText(context.getString(R.string.status_text_start), true);
    }

    BreakTimeModel getDataModel() {
        return mDataModel;
    }

    public void addBreakTime(View v) {
        Log.i(TAG, "addBreakTime: " + mBreakTime);
        if (!mStarted) {
            mStarted = true;
            mDataModel.setStartButtonText(mContext.getString(R.string.button_text_add));
            stopLocationUpdater();
        }
        mBreakTime += BLOCK;
        startCountDownTimer();
    }

    public void stopBreak(View v) {
        Log.i(TAG, "stopBreakTime: " + mBreakTime);
        if (mStarted) {
            mStarted = false;
            mDataModel.setStartButtonText(mContext.getString(R.string.button_text_start));
            startLocationUpdater();
        }
        mBreakTime = 0;
        mCountDownTimer.cancel();
        mDataModel.setStatusText(mContext.getString(R.string.status_text_start), true);
    }

    private void startLocationUpdater() {
        Intent intent = new Intent(mContext, MainService.class);
        intent.putExtra(MainService.ACTION_KEY, MainService.ACTION_START_LOCATION_REPORT);
        mContext.startService(intent);
    }

    private void stopLocationUpdater() {
        Intent intent = new Intent(mContext, MainService.class);
        intent.putExtra(MainService.ACTION_KEY, MainService.ACTION_STOP_LOCATION_REPORT);
        mContext.startService(intent);
    }

    private void startCountDownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mCountDownTimer = new CountDownTimer(mBreakTime, SECOND) {
            @Override
            public void onTick(long l) {
                mBreakTime = l;
                long sec = l / 1000;
                mDataModel.setStatusText(String.format(Locale.ENGLISH, "%02d:%02d", (sec / 60), (sec % 60)), false);
            }

            @Override
            public void onFinish() {
                mDataModel.setStatusText(mContext.getString(R.string.status_text_time_up), true);
                mDataModel.setStartButtonText(mContext.getString(R.string.button_text_start));
                startLocationUpdater();
            }
        };
        mCountDownTimer.start();
    }
}
