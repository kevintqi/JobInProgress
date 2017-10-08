package com.sebeca.app.jobinprogress.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Utility class for creating and switching Fragments
 */

public class FragmentSwitcher {
    public static final int FRAGMENT_LOGIN = 1;
    public static final int FRAGMENT_SIGNUP = 2;
    private static Container mContainer;

    public static void setContainer(Container container) {
        mContainer = container;
    }

    public static void to(int fragmentId, Bundle argument) {
        BaseFragment fragment = newFragment(fragmentId, argument);
        mContainer.switchTo(fragment, fragment.allowBack());
    }

    public static BaseFragment newFragment(int fragmentId, Bundle argument) {
        switch (fragmentId) {
            case FRAGMENT_LOGIN:
                return null;
            case FRAGMENT_SIGNUP:
                return new SignupFragment();
        }
        return null;
    }

    interface Container {
        void switchTo(Fragment fragment, boolean allowBack);
    }
}
