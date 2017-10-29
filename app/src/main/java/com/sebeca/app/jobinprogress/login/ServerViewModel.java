package com.sebeca.app.jobinprogress.login;

import android.content.Context;

import com.sebeca.app.jobinprogress.network.PersistentDataStore;

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
        PersistentDataStore dataStore = new PersistentDataStore(mContext);
        dataStore.putServerUrl(mDataModel.getUrl().get());
        FragmentSwitcher.back();
    }
}
