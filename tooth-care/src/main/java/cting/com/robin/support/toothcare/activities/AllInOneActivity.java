package cting.com.robin.support.toothcare.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.fragments.DailyRecordListFragment;
import cting.com.robin.support.toothcare.fragments.ProgressListFragment;
import cting.com.robin.support.toothcare.fragments.TodayFragment;
import cting.com.robin.support.toothcare.models.MessageEvent;
import cting.com.robin.support.toothcare.services.DataLoaderService;

public class AllInOneActivity extends BasePermissionCheckActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected static final String TAG = "cting/AllInOneActivity";
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    private boolean mIsAlreadyLoad = false;
    private boolean mIsMessageAlreadyRegistered=false;

    private Fragment mTodayFragment;
    private ProgressListFragment mProgressListFragment;
    private DailyRecordListFragment mDailyRecordListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        setContentView(R.layout.activity_all_in_one);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        end();
    }

    private void start() {
        if (mPermissionReady) {
            if (!mIsMessageAlreadyRegistered) {
                Log.i(TAG, "start: register event bus");
                EventBus.getDefault().register(this);
                mIsMessageAlreadyRegistered = true;
            }
            if (!mIsAlreadyLoad) {
                Log.i(TAG, "start: start load service");
                DataLoaderService.startActionLoad(this,DataLoaderService.DATA_SOURCE_EXTERNAL_JSON);
                mIsAlreadyLoad = true;
            }
        }
    }

    private void end() {
        if (mIsMessageAlreadyRegistered) {
            Log.i(TAG, "end: unregister event bus");
            EventBus.getDefault().unregister(this);
            mIsMessageAlreadyRegistered = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadFinished(MessageEvent msg) {
        if (msg.isLoadFinish()) {
            Log.i(TAG, "onLoadFinished: ");
            if (mTodayFragment == null) {
                mTodayFragment = new TodayFragment();
            }
            setFragment(mTodayFragment);
        }
    }

    protected void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.allinone_content_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            if (mTodayFragment == null) {
                mTodayFragment = new TodayFragment();
            }
            setFragment(mTodayFragment);
        } else if (id == R.id.nav_progress_list) {
            if (mProgressListFragment == null) {
                mProgressListFragment = new ProgressListFragment();
            }
            setFragment(mProgressListFragment);
        } else if (id == R.id.nav_daily_record_list) {
            if (mDailyRecordListFragment == null) {
                mDailyRecordListFragment = new DailyRecordListFragment();
            }
            setFragment(mDailyRecordListFragment);
        } else if (id == R.id.nav_backup_data) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
