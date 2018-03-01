package cting.com.robin.support.toothcare.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.support.toothcare.fragments.ProgressDetailFragment;

public class ProgressDetailActivity extends RobinListActivity {

    private static final String PROGRESS_RECORD_BUNDLE = "PROGRESS_RECORD_BUNDLE";

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        ProgressDetailFragment fragment = new ProgressDetailFragment();
        Bundle bundle = getIntent().getBundleExtra(PROGRESS_RECORD_BUNDLE);
        fragment.setArguments(bundle);
        addFragment(fragment);
    }

    public static final void launchWithBundle(Context context, Bundle bundle) {
        Log.i(TAG, "launch" + ProgressDetailActivity.class.getName() + " with Bundle "+bundle);
        Intent intent = new Intent(context, ProgressDetailActivity.class);
        intent.putExtra(PROGRESS_RECORD_BUNDLE, bundle);
        context.startActivity(intent);
    }
}
