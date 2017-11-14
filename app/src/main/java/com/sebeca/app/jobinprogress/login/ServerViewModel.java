package com.sebeca.app.jobinprogress.login;

import android.content.Context;

import com.sebeca.app.jobinprogress.data.ServerUrlDataStore;

public class ServerViewModel {
    private Context mContext;
    private ServerUrl mDataModel = new ServerUrl();
    private ServerUrlDataStore mDataStore;

    public ServerViewModel(Context ctx) {
        mContext = ctx;
        mDataStore = new ServerUrlDataStore(mContext);
        if (mDataStore.isAvailable()) {
            mDataModel.setUrl(mDataStore.get());
        }
    }

    public ServerUrl getDataModel() {
        return mDataModel;
    }

    public void onClickSet() {
        mDataStore.put(mDataModel.getUrl().get());
        FragmentSwitcher.back();
    }
}
