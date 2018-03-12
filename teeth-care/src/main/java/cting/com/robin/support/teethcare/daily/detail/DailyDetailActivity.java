package cting.com.robin.support.teethcare.daily.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.support.recyclerview.fragments.RobinListFragment;
import cting.com.robin.support.teethcare.MainActivity;

import static cting.com.robin.support.teethcare.daily.detail.DailyDetailFragment.ACTION_EDIT;
import static cting.com.robin.support.teethcare.daily.detail.DailyDetailFragment.DAILY_RECORD;
import static cting.com.robin.support.teethcare.daily.detail.DailyDetailFragment.DAILY_RECORD_DAY_INDEX;

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
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public static void launch(Context context, int dayIndex, boolean editMode) {
        Intent intent = new Intent(context, DailyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DAILY_RECORD_DAY_INDEX, dayIndex);
        bundle.putBoolean(ACTION_EDIT, editMode);
        intent.putExtra(DAILY_RECORD, bundle);
        context.startActivity(intent);
    }

}
