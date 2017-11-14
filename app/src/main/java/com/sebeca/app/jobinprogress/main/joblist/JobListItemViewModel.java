package com.sebeca.app.jobinprogress.main.joblist;

import android.util.Log;
import android.view.View;

/**
 * Created by kevinqi on 11/5/17.
 */

public class JobListItemViewModel {
    private Listener mListener;

    public JobListItemViewModel(Listener listener) {
        mListener = listener;
    }

    public void onClickView(View v) {
        mListener.onCLickView();
    }

    public void onClickAction(View v) {
        Log.i("KQ", "Click");
    }

    public interface Listener {
        void onCLickView();
    }
}
