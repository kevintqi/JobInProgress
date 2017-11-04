package com.sebeca.app.jobinprogress.main;

import android.os.Handler;
import android.os.Message;

/**
 * Base class for repeating an action until cancellation
 */

public abstract class ActionRepeater {
    private final int mMessageId;
    private final int mInterval;

    private boolean mCancelled = false;
    private Handler mHandler = new ActionHandler();

    protected ActionRepeater(int id, int interval) {
        mMessageId = id;
        mInterval = interval;
    }

    public synchronized void start(int delay) {
        mCancelled = false;
        mHandler.sendMessageDelayed(mHandler.obtainMessage(mMessageId), delay);
    }

    public synchronized void cancel() {
        mCancelled = true;
    }

    protected abstract void action(int id);

    private class ActionHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            synchronized (ActionRepeater.this) {
                if (mCancelled) {
                    return;
                }
                action(msg.what);
                sendMessageDelayed(obtainMessage(mMessageId), mInterval);
            }
        }
    }
}
