package com.sebeca.app.jobinprogress.login;

/**
 * Created by kevinqi on 10/28/17.
 */

public class SwitchServerViewModel {
    public void onClickSwitchToUrlSetting() {
        FragmentSwitcher.to(FragmentSwitcher.FRAGMENT_SERVER, null);
    }
}
