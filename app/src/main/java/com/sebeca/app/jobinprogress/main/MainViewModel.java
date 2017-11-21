package com.sebeca.app.jobinprogress.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by kevinqi on 11/20/17.
 */

public class MainViewModel extends AndroidViewModel {

    private Application mApp;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mApp = application;
        Intent intent = new Intent(mApp, MainService.class);
        intent.putExtra(MainService.ACTION_KEY, MainService.ACTION_START);
        mApp.startService(intent);
    }

    @Override
    protected void onCleared() {
        Intent intent = new Intent(mApp, MainService.class);
        mApp.stopService(intent);
    }
}
