package cting.com.robin.support.teethcare;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.teethcare.braces.BracesListFragment;
import cting.com.robin.support.teethcare.daily.DailyListFragment;

public class MainActivity extends BasePermissionCheckActivity {
    private static final String FRAGMENT_DAILY = "fragment_daily";
    private static final String FRAGMENT_BRACES = "fragment_braces";
    private static final int ITEM_ID_SWITCH = 0;
    private AddNewDayManager addNewDayManager;

    @BindView(R.id.today_btn)
    FloatingActionButton todayBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    private DailyListFragment mDailyFragment;
    private BracesListFragment mBracesFragment;
    private String mCurrentFragment = FRAGMENT_DAILY;

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        addNewDayManager = new AddNewDayManager(this);
        setSupportActionBar(toolbar);
        setFragment(mCurrentFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.today_btn)
    public void gotoToday() {
//        Toast.makeText(this, "goto today!", Toast.LENGTH_SHORT).show();
        addNewDayManager.gotoToday();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, ITEM_ID_SWITCH, 0, R.string.menu_title_switch)
                .setIcon(android.R.drawable.ic_dialog_info);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ITEM_ID_SWITCH) {
            if (mCurrentFragment == FRAGMENT_DAILY) {
                setFragment(FRAGMENT_BRACES);
            } else if (mCurrentFragment == FRAGMENT_BRACES) {
                setFragment(FRAGMENT_DAILY);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(String fragment) {
        if (FRAGMENT_DAILY.equals(fragment)) {
            if (mDailyFragment == null) {
                mDailyFragment = new DailyListFragment();
            }
            setFragment(mDailyFragment);
            mCurrentFragment = fragment;
        } else if (FRAGMENT_BRACES.equals(fragment)) {
            if (mBracesFragment == null) {
                mBracesFragment = new BracesListFragment();
            }
            setFragment(mBracesFragment);
            mCurrentFragment = fragment;
        }
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
