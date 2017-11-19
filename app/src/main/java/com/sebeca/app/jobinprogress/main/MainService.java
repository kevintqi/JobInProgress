package com.sebeca.app.jobinprogress.main;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sebeca.app.jobinprogress.locator.LocationReporter;
import com.sebeca.app.jobinprogress.locator.LocationUpdater;

public class MainService extends Service {
    public static final String ACTION_KEY = "ACTION";
    public static final int ACTION_START_LOCATION_REPORT = 1;
    public static final int ACTION_STOP_LOCATION_REPORT = 2;

    private static final String TAG = MainService.class.getSimpleName();
    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    private LocationReporter mLocationReporter;
    private LocationUpdater mLocationUpdater;

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler();
        mLocationReporter = new LocationReporter(this);
        mLocationUpdater = new LocationUpdater(this);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopLocationReport();
        mServiceLooper.quit();
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startLocationReport() {
        mLocationUpdater.start();
        mLocationReporter.start(LocationReporter.INTERVAL);
    }

    private void stopLocationReport() {
        mLocationUpdater.stop();
        mLocationReporter.cancel();
    }

    private final class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = (Intent) msg.obj;
            if (intent == null) {
                Log.e(TAG, "intent==null");
                return;
            }
            Bundle extra = intent.getExtras();
            if (extra == null) {
                Log.e(TAG, "expect bundleExtra");
                return;
            }
            int action = extra.getInt(ACTION_KEY);
            switch (action) {
                case ACTION_START_LOCATION_REPORT:
                    Log.i(TAG, "ACTION_START_LOCATION_REPORT");
                    startLocationReport();
                    break;
                case ACTION_STOP_LOCATION_REPORT:
                    Log.i(TAG, "ACTION_STOP_LOCATION_REPORT");
                    stopLocationReport();
                    break;
                default:
                    break;
            }
        }
    }
}
