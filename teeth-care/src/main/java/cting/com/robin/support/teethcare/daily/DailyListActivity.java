package cting.com.robin.support.teethcare.daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import cting.com.robin.support.recyclerview.activities.RobinListActivity;
import cting.com.robin.support.teethcare.MainActivity;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;

import static cting.com.robin.support.teethcare.daily.DailyListFragment.BRACES_RECORD;
import static cting.com.robin.support.teethcare.daily.DailyListFragment.BRACES_RECORD_INDEX;


public class DailyListActivity extends RobinListActivity {

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Fragment fragment = new DailyListFragment();
        Bundle bundle = getIntent().getBundleExtra(BRACES_RECORD);
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

    public static void launch(Context context, int bracesIndex) {
        Intent intent = new Intent(context, DailyListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BRACES_RECORD_INDEX, bracesIndex);
        intent.putExtra(BRACES_RECORD, bundle);
        context.startActivity(intent);
    }
}
