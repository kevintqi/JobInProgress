package com.sebeca.app.jobinprogress.locator;

import android.app.IntentService;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocationService extends IntentService {
    public static final String ACTION_KEY = "ACTION";
    public static final int ACTION_START = 1;
    public static final int ACTION_STOP = 2;
    public static final int IDLE = 30000;
    private static final String TAG = LocationService.class.getSimpleName();
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 10f;
    private final LocationUpdater mLocationUpdater = new LocationUpdater();
    private LocationManager mLocationManager;
    private LocationReporter mLocationReporter;

    private boolean mShouldContinue = true;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (mShouldContinue) {
                try {
                    Thread.sleep(IDLE);
                    Object[] items = mLocationUpdater.popItemQueue();
                    mLocationReporter.reportData(items);
                } catch (InterruptedException e) {
                    Log.e(TAG, "Service Interrupted! ", e);
                }
            }
        }
    };

    public LocationService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationReporter = new LocationReporter(this.getApplicationContext());
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        stopAction();
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
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
                startAction();
                break;
            case ACTION_STOP:
                stopAction();
                break;
            default:
                break;
        }
    }

    private void startAction() {
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationUpdater
            );
        } catch (SecurityException e) {
            Log.e(TAG, "fail to request location update, ignore", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "network provider does not exist, " + e.getMessage());
        }
        new Thread(mRunnable).run();
    }

    private void stopAction() {
        mShouldContinue = false;
        mLocationManager.removeUpdates(mLocationUpdater);
    }
}
