package cting.com.robin.support.teethcare;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.teethcare.braces.BracesListFragment;
import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.braces.DateSlice;
import cting.com.robin.support.teethcare.daily.DailyListFragment;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;
import cting.com.robin.support.teethcare.daily.detail.TimeSlice;
import cting.com.robin.support.teethcare.repository.DataGenerator;
import cting.com.robin.support.teethcare.repository.MyRepositoryService;
import cting.com.robin.support.teethcare.repository.MySource;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class MainActivity extends BasePermissionCheckActivity
        implements DialogInterface.OnClickListener {
    private static final String FRAGMENT_DAILY = "fragment_daily";
    private static final String FRAGMENT_BRACES = "fragment_braces";
    private static final int ITEM_ID_SWITCH = 0;

    @BindView(R.id.today_btn)
    FloatingActionButton todayBtn;

    private Unbinder unbinder;

    private DailyListFragment mDailyFragment;
    private BracesListFragment mBracesFragment;
    private String mCurrentFragment = FRAGMENT_DAILY;

    private int mCurrentDayIndex;
    private String mTodayDate;
    private int mLastBracesIndex;
    private DailyRecord mToday;

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFragment(mCurrentFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.today_btn)
    public void gotoToday(FloatingActionButton button) {
//        Toast.makeText(this, "goto today!", Toast.LENGTH_SHORT).show();
        DataGenerator generator = MySource.getInstance().getGenerator();
        BracesRecord lastBraces = generator.getLastBraces(getApplicationContext());
        DailyRecord lastDay = generator.getLastDay(getApplicationContext());
        mToday = null;
        mTodayDate = TimeFormatHelper.formatToday();
        if (lastBraces == null) {
            //no braces
            mLastBracesIndex = 0;
            mCurrentDayIndex = 0;
            addFirstBraces();
        } else if (lastDay.getDate().equals(mTodayDate)) {
            //today exists
            mToday = lastDay;
            mLastBracesIndex = lastBraces.getIndex();
            mCurrentDayIndex = mToday.getIndex();
        } else if (lastBraces.getTotalNumeric() >= 8) {
            //check if current braces finished
            mLastBracesIndex = lastBraces.getIndex();
            mCurrentDayIndex = lastDay.getIndex() + 1;
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Do you want to use a new braces?")
                    .setPositiveButton(android.R.string.ok, this)
                    .setNegativeButton(android.R.string.cancel, this);
            builder.create()
                    .show();
        } else {
            //new today
            mLastBracesIndex = lastBraces.getIndex();
            mCurrentDayIndex = lastDay.getIndex() + 1;
            addDay(mCurrentDayIndex, mTodayDate);
            generator.addDay(mToday);
            generator.updateLastBraces(mToday.getDate());
            Toast.makeText(this, "It's a new day!", Toast.LENGTH_SHORT).show();
        }
        gotoToday();
    }

    private void gotoToday() {
        if (mToday != null) {
            DailyDetailActivity.launch(this, mToday, true);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            //new today in a new braces
            addBraces(mLastBracesIndex);
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            //continue add aday
            addDay(mCurrentDayIndex, mTodayDate);
        }
        gotoToday();
    }

    private void addFirstBraces() {
        addBraces(1);
    }

    private BracesRecord addBraces(int index) {
        addDay(mCurrentDayIndex, mTodayDate);
        BracesRecord braces = new BracesRecord.Builder()
                .index(index)
                .dateSlice(new DateSlice.Builder()
                        .from(mTodayDate)
                        .to(mTodayDate)
                        .build())
                .build();
        Log.i(TAG, "addBraces: " + braces);
        MySource.getInstance().getGenerator().addBraces(braces);
        MyRepositoryService.startActionSave(this);
        return braces;
    }

    private DailyRecord addDay(int index, String todayDate) {
        Log.i(TAG, "addDay: " + index + " " + todayDate);
        mToday = new DailyRecord.Builder()
                .index(index)
                .date(todayDate)
                .timeSlice(new TimeSlice.Builder()
                        .from("00:00")
                        .to("00:00")
                        .build())
                .build();
        MySource.getInstance().getGenerator().addDay(mToday);
        MyRepositoryService.startActionSave(this);
        return mToday;
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
}
