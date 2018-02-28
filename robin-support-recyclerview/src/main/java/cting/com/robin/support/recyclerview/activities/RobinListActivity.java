package cting.com.robin.support.recyclerview.activities;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.recyclerview.R;

public abstract class RobinListActivity extends BasePermissionCheckActivity {

    public static final String TAG = "cting/list/act";

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        setContentView(R.layout.activity_robin_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    protected void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit();
    }

}
