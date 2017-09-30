package com.sebeca.app.jobinprogress.breaktime;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.sebeca.app.jobinprogress.locator.LocationUpdater;

public class BreakTimeViewModel {
    private static final String TAG = BreakTimeViewModel.class.getSimpleName();
    private static final long BLOCK = 30 * 60 * 1000;
    private Context mContext;
    private long mBreakTime = 0L;
    private boolean mStarted = false;

    BreakTimeViewModel(Context context) {
        mContext = context;
    }

    public void addBreakTime(View v) {
        Log.i(TAG, "addBreakTime: " + mBreakTime);
        if (!mStarted) {
            mStarted = true;
            Log.i(TAG, "stop service:" + mStarted);
            Intent intent = new Intent(mContext, LocationUpdater.class);
            intent.putExtra(LocationUpdater.ACTION_KEY, LocationUpdater.ACTION_STOP);
            mContext.startService(intent);
        }
        mBreakTime += BLOCK;
    }

    public void stopBreak(View v) {
        Log.i(TAG, "stopBreakTime: " + mBreakTime);
        if (mStarted) {
            mStarted = false;
            Intent intent = new Intent(mContext, LocationUpdater.class);
            intent.putExtra(LocationUpdater.ACTION_KEY, LocationUpdater.ACTION_START);
            mContext.startService(intent);
        }
        mBreakTime = 0;
    }
}
