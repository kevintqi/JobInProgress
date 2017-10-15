package com.sebeca.app.jobinprogress.login;

import android.support.annotation.Nullable;

/**
 * Callback offered by Fragment, which allows ViewModel to update Views
 */

public interface ViewModelListener {
    /**
     * Called if name field is invalid
     *
     * @param msg Error message
     */
    void onNameError(@Nullable String msg);

    /**
     * Called if email field is invalid
     * @param msg Error message
     */
    void onEmailError(@Nullable String msg);

    /**
     * Called if password field is invalid
     * @param msg Error message
     */
    void onPasswordError(@Nullable String msg);

    /**
     * Called when async action has started
     */
    void onActionProgress();

    /**
     * Called when async action is done
     */
    void onActionDone(boolean success);
}
