package com.sebeca.app.jobinprogress.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sebeca.app.jobinprogress.R;


/**
 * A login activity for login and sign up
 */
public class LoginActivity extends AppCompatActivity implements FragmentSwitcher.Container {
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.login_container, mFragment).commit();
        FragmentSwitcher.setContainer(this);
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
}

