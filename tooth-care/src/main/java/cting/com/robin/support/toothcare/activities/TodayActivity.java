package cting.com.robin.support.toothcare.activities;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.support.toothcare.fragments.TodayFragment;

/**
 * Created by cting on 2018/3/3.
 */

public class TodayActivity extends RobinListActivity {

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        addFragment(new TodayFragment());
    }
}
