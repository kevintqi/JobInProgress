package com.sebeca.app.jobinprogress.di;

import com.sebeca.app.jobinprogress.data.DataStore;
import com.sebeca.app.jobinprogress.locator.LocationReporter;
import com.sebeca.app.jobinprogress.locator.LocationUpdater;
import com.sebeca.app.jobinprogress.login.LoginActivity;
import com.sebeca.app.jobinprogress.main.joblist.JobListItemViewModel;
import com.sebeca.app.jobinprogress.main.joblist.JobListUpdater;
import com.sebeca.app.jobinprogress.main.joblist.JobListViewModel;
import com.sebeca.app.jobinprogress.main.map.JobMapsViewModel;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(DataStore dataStore);

    void inject(MyObjectRequest myObjectRequest);

    void inject(JobListViewModel jobListViewModel);

    void inject(JobListItemViewModel jobListItemViewModel);

    void inject(JobListUpdater jobListUpdater);

    void inject(LocationUpdater locationUpdater);

    void inject(LocationReporter locationReporter);

    void inject(JobMapsViewModel jobMapsViewModel);

    void inject(LoginActivity loginActivity);
}
