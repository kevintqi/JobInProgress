package com.sebeca.app.jobinprogress.database;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Base class for running a task on a HandlerThread
 */
public class DataTaskRunner {
    private final Handler mHandler;

    public DataTaskRunner(String threadName) {
        HandlerThread thread = new HandlerThread(threadName);
        thread.start();
        mHandler = new Handler(thread.getLooper());
    }

    protected void run(Runnable runner) {
        mHandler.post(runner);
    }
}
