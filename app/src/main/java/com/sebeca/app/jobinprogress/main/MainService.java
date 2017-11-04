package com.sebeca.app.jobinprogress.main;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sebeca.app.jobinprogress.locator.LocationDataQueue;
import com.sebeca.app.jobinprogress.locator.LocationReporter;
import com.sebeca.app.jobinprogress.main.joblist.JobListRequester;

public class MainService extends Service {
    public static final String ACTION_KEY = "ACTION";
    public static final int ACTION_START = 1;
    public static final int ACTION_STOP = 2;

    private static final String TAG = MainService.class.getSimpleName();
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 10f;
    private final LocationDataQueue mLocationDataQueue = new LocationDataQueue();
    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    private LocationManager mLocationManager;
    private LocationReporter mLocationReporter;
    private JobListRequester mJobListRequester;

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler();
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        mLocationReporter = new LocationReporter(this, mLocationDataQueue);
        mJobListRequester = new JobListRequester(this);
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
        stopAction();
        mServiceLooper.quit();
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startAction() {
        try {
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        LOCATION_INTERVAL,
                        LOCATION_DISTANCE,
                        mLocationDataQueue
                );
            } else {
                Log.w(TAG, "LocationProvider not enabled");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "fail to request location update, ignore", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "network provider does not exist, " + e.getMessage());
        }
        mLocationReporter.start(LocationReporter.INTERVAL);
        mJobListRequester.start(0);
    }

    private void stopAction() {
        mLocationManager.removeUpdates(mLocationDataQueue);
        mLocationReporter.cancel();
        mJobListRequester.cancel();
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
                case ACTION_START:
                    Log.i(TAG, "ACTION_START");
                    startAction();
                    break;
                case ACTION_STOP:
                    Log.i(TAG, "ACTION_STOP");
                    stopAction();
                    break;
                default:
                    break;
            }
        }
    }
}
