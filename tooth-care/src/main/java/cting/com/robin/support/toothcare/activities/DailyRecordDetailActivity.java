package cting.com.robin.support.toothcare.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.toothcare.fragments.DailyRecordDetailFragment;


public class DailyRecordDetailActivity extends RobinListActivity {

    public static final String DAILY_RECORD_BUNDLE = "DAILY_RECORD_BUNDLE";

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        RobinListFragment fragment = new DailyRecordDetailFragment();
        Bundle bundle = getIntent().getBundleExtra(DAILY_RECORD_BUNDLE);
        fragment.setArguments(bundle);
        addFragment(fragment);
    }

    public static final void launchWithBundle(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DailyRecordDetailActivity.class);
        intent.putExtra(DAILY_RECORD_BUNDLE, bundle);
        context.startActivity(intent);
    }
}
