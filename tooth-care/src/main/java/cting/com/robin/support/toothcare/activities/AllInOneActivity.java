package cting.com.robin.support.toothcare.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.toothcare.R;
import cting.com.robin.support.toothcare.fragments.DailyRecordListFragment;
import cting.com.robin.support.toothcare.fragments.MainFragment;
import cting.com.robin.support.toothcare.fragments.ProgressListFragment;
import cting.com.robin.support.toothcare.fragments.TodayFragment;
import cting.com.robin.support.toothcare.models.MessageEvent;
import cting.com.robin.support.toothcare.services.DataLoaderService;

public class AllInOneActivity extends BasePermissionCheckActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected static final String TAG = "cting/AllInOneActivity";
    public static final String FRAGMENT_TODAY = "fragment_today";
    public static final String FRAGMENT_PROGRESS_LIST = "fragment_progress_list";
    public static final String FRAGMENT_DAILY_RECORD_LIST = "fragment_daily_record";
    public static final String FRAGMENT_MAIN = "fragment_main";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    private boolean mIsAlreadyLoad = false;
    private boolean mIsMessageAlreadyRegistered = false;

    private Fragment mTodayFragment;
    private ProgressListFragment mProgressListFragment;
    private DailyRecordListFragment mDailyRecordListFragment;
    private MainFragment mMainFragment;
    private Fragment mCurrentFragment;

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
                DataLoaderService.startActionLoad(this, DataLoaderService.DATA_SOURCE_EXTERNAL_JSON);
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
            setFragment(FRAGMENT_MAIN);
        }
    }

    public void setFragment(String fragment) {
        if (FRAGMENT_TODAY.equals(fragment)) {
            if (mTodayFragment == null) {
                mTodayFragment = new TodayFragment();
            }
            mCurrentFragment = mTodayFragment;
            mNavView.setCheckedItem(R.id.nav_today);
        } else if (FRAGMENT_PROGRESS_LIST.equals(fragment)) {
            if (mProgressListFragment == null) {
                mProgressListFragment = new ProgressListFragment();
            }
            mCurrentFragment = mProgressListFragment;
            mNavView.setCheckedItem(R.id.nav_progress_list);
        } else if (FRAGMENT_DAILY_RECORD_LIST.equals(fragment)) {
            if (mDailyRecordListFragment == null) {
                mDailyRecordListFragment = new DailyRecordListFragment();
            }
            mCurrentFragment = mDailyRecordListFragment;
            mNavView.setCheckedItem(R.id.nav_daily_record_list);
        } else if (FRAGMENT_MAIN.equals(fragment)) {
            if (mMainFragment == null) {
                mMainFragment = new MainFragment();
            }
            mCurrentFragment = mMainFragment;
            mNavView.setCheckedItem(R.id.nav_homepage);
        }
        if (mCurrentFragment != null) {
            setFragment(mCurrentFragment);
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
        } else if (mCurrentFragment != mMainFragment) {
            setFragment(FRAGMENT_MAIN);
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
            setFragment(FRAGMENT_TODAY);
        } else if (id == R.id.nav_progress_list) {
            setFragment(FRAGMENT_PROGRESS_LIST);
        } else if (id == R.id.nav_daily_record_list) {
            setFragment(FRAGMENT_DAILY_RECORD_LIST);
        } else if (id == R.id.nav_homepage) {
            setFragment(FRAGMENT_MAIN);
        } else if (id == R.id.nav_backup_data) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
