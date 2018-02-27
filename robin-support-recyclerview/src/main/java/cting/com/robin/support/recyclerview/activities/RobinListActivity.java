package cting.com.robin.support.recyclerview.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.recyclerview.R;

public abstract class RobinListActivity extends BasePermissionCheckActivity {

    public static final String TAG = "cting/list/act";

    protected abstract int getLayoutId();

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        setContentView(R.layout.activity_robin_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewStub viewStub = findViewById(R.id.fragment_view_stub);
        viewStub.setLayoutResource(getLayoutId());
        viewStub.inflate();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
