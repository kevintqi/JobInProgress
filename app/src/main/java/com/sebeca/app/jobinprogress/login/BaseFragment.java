package com.sebeca.app.jobinprogress.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kevinqi on 10/5/17.
 */

public abstract class BaseFragment extends Fragment {

    public abstract boolean allowBack();

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

    @Override
    public abstract void onViewCreated(View view, @Nullable Bundle savedInstanceState);
}
