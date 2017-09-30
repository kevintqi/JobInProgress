package com.sebeca.app.jobinprogress.activities;

/**
 * Created by kevinqi on 9/5/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sebeca.app.jobinprogress.breaktime.BreakTimeFragment;
import com.sebeca.app.jobinprogress.joblist.JobListFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    public static final int SECTION_JOB_LIST = 0;
    public static final int SECTION_BREAK_TIMER = 1;
    public static final int SECTION_SETTINGS = 2;
    public static final int SECTION_TOTAL = 3;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case SECTION_JOB_LIST:
                return new JobListFragment();
            case SECTION_BREAK_TIMER:
                return new BreakTimeFragment();
            case SECTION_SETTINGS:
                return new JobListFragment();
        }
        return new JobListFragment();
    }

    @Override
    public int getCount() {
        return SECTION_TOTAL;
    }
}
