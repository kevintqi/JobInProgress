package com.sebeca.app.jobinprogress.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sebeca.app.jobinprogress.R;

import static com.sebeca.app.jobinprogress.main.SectionsPagerAdapter.SECTION_BREAK_TIMER;
import static com.sebeca.app.jobinprogress.main.SectionsPagerAdapter.SECTION_JOB_LIST;
import static com.sebeca.app.jobinprogress.main.SectionsPagerAdapter.SECTION_MAPS;
import static com.sebeca.app.jobinprogress.main.SectionsPagerAdapter.SECTION_SETTINGS;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Toolbar appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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
                            mViewPager.setCurrentItem(SECTION_JOB_LIST);
                            appBar.setTitle(item.getTitle());
                        }
                        if (item.getItemId() == R.id.jobMapsView) {
                            mViewPager.setCurrentItem(SECTION_MAPS);
                            appBar.setTitle(item.getTitle());
                        }
                        if (item.getItemId() == R.id.breakTimeView) {
                            mViewPager.setCurrentItem(SECTION_BREAK_TIMER);
                            appBar.setTitle(item.getTitle());
                        }
                        if (item.getItemId() == R.id.settingsView) {
                            mViewPager.setCurrentItem(SECTION_SETTINGS);
                            appBar.setTitle(item.getTitle());
                        }
                        return true;
                    }
                });
    }
}
