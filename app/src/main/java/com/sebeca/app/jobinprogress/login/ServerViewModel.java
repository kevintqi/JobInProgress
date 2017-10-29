package com.sebeca.app.jobinprogress.login;

import android.content.Context;

import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;

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
        ServerUrlDataStore dataStore = new ServerUrlDataStore(mContext);
        dataStore.put(mDataModel.getUrl().get());
        FragmentSwitcher.back();
    }
}
