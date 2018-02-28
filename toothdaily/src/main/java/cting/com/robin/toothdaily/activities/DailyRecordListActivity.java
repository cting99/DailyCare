package cting.com.robin.toothdaily.activities;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.toothdaily.fragments.DailyRecordListFragment;

/**
 * Created by cting on 2018/2/28.
 */

public class DailyRecordListActivity extends RobinListActivity {

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        addFragment(new DailyRecordListFragment());
    }
}
