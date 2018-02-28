package cting.com.robin.support.toothcare.activities;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.support.toothcare.fragments.DailyRecordListFragment;

public class DailyRecordListActivity extends RobinListActivity {


    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();

        addFragment(new DailyRecordListFragment());
    }
}
