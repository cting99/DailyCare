package cting.com.robin.support.recyclerview.activities;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import cting.com.robin.support.common.activities.BasePermissionCheckActivity;
import cting.com.robin.support.recyclerview.R;

public abstract class RobinListActivity extends BasePermissionCheckActivity{

    public static String TAG = "cting/act/";

    public RobinListActivity() {
        TAG += getClass().getSimpleName();
    }

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        setContentView(R.layout.activity_robin_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        setupAddButton();
    }
/*
    protected void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }*/

    protected void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit();
    }
/*
    @Override
    public void onClick(View v) {

    }*/
}
