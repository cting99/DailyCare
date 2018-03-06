package cting.com.robin.support.teethcare.daily;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;

public class DailyListActivity extends RobinListActivity {

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();

        addFragment(new DailyListFragment());
    }

}
