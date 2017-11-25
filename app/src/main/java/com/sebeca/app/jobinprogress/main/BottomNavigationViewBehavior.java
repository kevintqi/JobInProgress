package com.sebeca.app.jobinprogress.main;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by kevinqi on 11/22/17.
 */

public class BottomNavigationViewBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    private int mViewHeight;

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, BottomNavigationView view, int layoutDirection) {
        mViewHeight = view.getHeight();
        return super.onLayoutChild(parent, view, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView view, View directTargetChild, View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView view, View target, int dx, int dy, int[] consumed, int type) {
        if (dy > 0) {
            hideNavigation(view);
        } else if (dy < 0) {
            showNavigation(view);
        }
    }

    private void showNavigation(BottomNavigationView view) {
        view.animate().translationY(0);
    }

    private void hideNavigation(BottomNavigationView view) {
        view.animate().translationY(mViewHeight);
    }
}
