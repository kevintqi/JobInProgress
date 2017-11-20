package com.sebeca.app.jobinprogress.di;

import com.sebeca.app.jobinprogress.login.LoginActivity;
import com.sebeca.app.jobinprogress.main.joblist.JobListViewModel;
import com.sebeca.app.jobinprogress.network.MyObjectRequest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(JobListViewModel jobListViewModel);

    void inject(MyObjectRequest myObjectRequest);

    void inject(LoginActivity loginActivity);
}
