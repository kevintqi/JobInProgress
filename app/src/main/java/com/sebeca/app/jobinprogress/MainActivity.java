package com.sebeca.app.jobinprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sebeca.app.jobinprogress.locator.LocationService;

import static com.sebeca.app.jobinprogress.SectionsPagerAdapter.SECTION_BREAK_TIMER;
import static com.sebeca.app.jobinprogress.SectionsPagerAdapter.SECTION_JOB_LIST;
import static com.sebeca.app.jobinprogress.SectionsPagerAdapter.SECTION_SETTINGS;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(SECTION_JOB_LIST).setIcon(R.mipmap.ic_view_list);
        tabLayout.getTabAt(SECTION_BREAK_TIMER).setIcon(R.mipmap.ic_timer);
        tabLayout.getTabAt(SECTION_SETTINGS).setIcon(R.mipmap.ic_settings);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocationService.class);
        intent.putExtra(LocationService.ACTION_KEY, LocationService.ACTION_START);
        startService(intent);
    }

    @Override
    protected void onStop() {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
        super.onStop();
    }

}
