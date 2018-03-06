package cting.com.robin.support.teethcare.daily.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.teethcare.daily.DailyListActivity;
import cting.com.robin.support.teethcare.daily.DailyRecord;

import static cting.com.robin.support.teethcare.daily.detail.DailyDetailFragment.ACTION_EDIT;
import static cting.com.robin.support.teethcare.daily.detail.DailyDetailFragment.DAILY_RECORD;
import static cting.com.robin.support.teethcare.daily.detail.DailyDetailFragment.DAILY_RECORD_DATE;

public class DailyDetailActivity extends RobinListActivity {

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RobinListFragment fragment = new DailyDetailFragment();
        Bundle bundle = getIntent().getBundleExtra(DAILY_RECORD);
        fragment.setArguments(bundle);
        addFragment(fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(this, DailyListActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void launch(Context context, DailyRecord item, boolean editMode) {
        Intent intent = new Intent(context, DailyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DAILY_RECORD_DATE, item.getDate());
        bundle.putBoolean(ACTION_EDIT, editMode);
        intent.putExtra(DAILY_RECORD, bundle);
        context.startActivity(intent);
    }

}
