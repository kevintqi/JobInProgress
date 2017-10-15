package com.sebeca.app.jobinprogress.login;

import android.support.annotation.Nullable;

/**
 * Created by kevinqi on 10/14/17.
 */

public interface ViewModelListener {
    void onNameError(@Nullable String msg);

    void onEmailError(@Nullable String msg);

    void onPasswordError(@Nullable String msg);

    void onActionProgress();

    void onActionDone();
}
