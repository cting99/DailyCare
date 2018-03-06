package cting.com.robin.support.teethcare.braces;

import android.os.Bundle;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;

public class BracesListActivity extends RobinListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        addFragment(new BracesListFragment());
    }
}
