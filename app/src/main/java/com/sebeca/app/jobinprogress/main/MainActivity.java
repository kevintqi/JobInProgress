package com.sebeca.app.jobinprogress.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sebeca.app.jobinprogress.R;
import com.sebeca.app.jobinprogress.main.breaktime.BreakTimeFragment;
import com.sebeca.app.jobinprogress.main.joblist.JobListFragment;
import com.sebeca.app.jobinprogress.main.map.JobMapsFragment;
import com.sebeca.app.jobinprogress.main.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Toolbar appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        bottomNavigation.setSelectedItemId(R.id.jobListView);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.jobListView) {
                            pushFragment(new JobListFragment());
                            appBar.setTitle(item.getTitle());
                        }
                        if (item.getItemId() == R.id.jobMapsView) {
                            pushFragment(new JobMapsFragment());
                            appBar.setTitle(item.getTitle());
                        }
                        if (item.getItemId() == R.id.breakTimeView) {
                            pushFragment(new BreakTimeFragment());
                            appBar.setTitle(item.getTitle());
                        }
                        if (item.getItemId() == R.id.settingsView) {
                            pushFragment(new SettingsFragment());
                            appBar.setTitle(item.getTitle());
                        }
                        return true;
                    }
                });
    }

    private void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        }
    }

}
