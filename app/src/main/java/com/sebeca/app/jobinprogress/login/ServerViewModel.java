package com.sebeca.app.jobinprogress.login;

import android.content.Context;

import com.sebeca.app.jobinprogress.network.MyRequestQueue;

public class ServerViewModel {
    private Context mContext;
    private ServerUrl mDataModel = new ServerUrl();

    public ServerViewModel(Context ctx) {
        mContext = ctx;
    }

    public ServerUrl getDataModel() {
        return mDataModel;
    }

    public void onClickSet() {
        MyRequestQueue.getInstance(mContext).setServerUrl(mDataModel.getUrl().get());
        FragmentSwitcher.back();
    }
}
