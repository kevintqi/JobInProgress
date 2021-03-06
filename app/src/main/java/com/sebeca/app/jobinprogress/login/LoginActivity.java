package com.sebeca.app.jobinprogress.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.data.SessionCookieDataStore;
import com.sebeca.app.jobinprogress.di.App;
import com.sebeca.app.jobinprogress.main.MainActivity;
import com.sebeca.app.jobinprogress.network.MyRequestQueue;

import javax.inject.Inject;


/**
 * A login activity for login and sign up
 */
public class LoginActivity extends AppCompatActivity implements FragmentSwitcher.Container {
    @Inject
    MyRequestQueue mMyRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_login);
        FragmentSwitcher.setContainer(this);
        showInitialFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMyRequestQueue.start();
    }

    @Override
    public void switchTo(Fragment fragment, boolean allowBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_container, fragment);
        if (allowBack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

    }

    @Override
    public void backToPrevious() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

    private void showInitialFragment() {
        SessionCookieDataStore dataStore = new SessionCookieDataStore(this);
        if (!dataStore.isAvailable()) {
            FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_LOGIN, null);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

