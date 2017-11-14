package com.sebeca.app.jobinprogress.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sebeca.app.jobinprogress.R;

import static com.sebeca.app.jobinprogress.main.SectionsPagerAdapter.SECTION_BREAK_TIMER;
import static com.sebeca.app.jobinprogress.main.SectionsPagerAdapter.SECTION_JOB_LIST;
import static com.sebeca.app.jobinprogress.main.SectionsPagerAdapter.SECTION_SETTINGS;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

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

        Intent intent = new Intent(this, MainService.class);
        intent.putExtra(MainService.ACTION_KEY, MainService.ACTION_START);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, MainService.class);
        stopService(intent);
        super.onDestroy();

    }
}
