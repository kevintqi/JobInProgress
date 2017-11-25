package com.sebeca.app.jobinprogress.util;

import android.os.Handler;
import android.os.Message;

/**
 * A timer periodically reports the elapsed duration. The timer may be paused any time.
 */

public class ElapsedTimer {

    private static final int ID = 121;
    private final int mInterval;
    private final Callback mCallback;
    private final State mState = new State();
    private final TimerHandler mHandler = new TimerHandler();
    private boolean mCancelled = false;

    public ElapsedTimer(int interval, Callback callback) {
        mInterval = interval;
        mCallback = callback;
    }

    public void start() {
        mCancelled = false;
        mState.startTime = System.currentTimeMillis();
        mState.totalDuration = 0;
        mState.lastPausedTime = 0L;
        mState.totalPausedDuration = 0;
        mState.lastPausedDuration = 0;

        mCallback.onUpdate(mState);
        mHandler.sendMessage(mHandler.obtainMessage(ID));
    }

    public void pause() {
        mState.lastPausedTime = System.currentTimeMillis();
        mState.lastPausedDuration = 0;
        updateDurations();
    }

    public void resume() {
        mState.lastPausedTime = 0L;
        mState.totalPausedDuration += mState.lastPausedDuration;
        mState.lastPausedDuration = 0;
        updateDurations();
    }

    public void stop() {
        updateDurations();
        mCancelled = true;
        mHandler.removeMessages(ID);
    }

    private void tick() {
        updateDurations();
        mCallback.onTick();
    }

    private void updateDurations() {
        long currentTime = System.currentTimeMillis();
        mState.totalDuration = (int) (currentTime - mState.startTime);
        if (mState.lastPausedTime != 0L) {
            mState.lastPausedDuration = (int) (currentTime - mState.lastPausedTime);
        }
        mCallback.onUpdate(mState);
    }

    public interface Callback {
        void onTick();

        void onUpdate(State state);
    }

    public static class State {
        public long startTime = 0L;
        public int totalDuration = 0;
        public long lastPausedTime = 0L;
        public int lastPausedDuration = 0;
        public int totalPausedDuration = 0;
    }

    private class TimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ID) {
                if (!mCancelled) {
                    tick();
                    sendMessageDelayed(obtainMessage(ID), mInterval);
                }
            }
        }
    }
}
