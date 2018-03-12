package cting.com.robin.support.teethcare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;
import cting.com.robin.support.teethcare.braces.BracesListFragment;
import cting.com.robin.support.teethcare.daily.DailyListFragment;
import cting.com.robin.support.teethcare.daily.today.NewDayManager;
import cting.com.robin.support.teethcare.repository.MessageEvent;
import cting.com.robin.support.teethcare.repository.MyRepositoryService;
import cting.com.robin.support.teethcare.transformers.DepthPageTransformer;

public class MainActivity extends BasePermissionCheckActivity {
    private static final int FRAGMENT_DAILY = 0;
    private static final int FRAGMENT_BRACES = 1;
    private static final int ITEM_ID_SWITCH = 0;
    private static final int ITEM_ID_BACKUP = 1;
    private NewDayManager addNewDayManager;

    @BindView(R.id.today_btn)
    FloatingActionButton todayBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unbinder;

//    private DailyListFragment mDailyFragment;
//    private BracesListFragment mBracesFragment;
    private int mCurrentFragment = FRAGMENT_DAILY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPermissionReady() {
        super.onPermissionReady();
        MyRepositoryService.startActionCheckDatabase(this);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        addNewDayManager = new NewDayManager(this);
        setSupportActionBar(toolbar);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
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
//        MenuItem item = menu.add(0, ITEM_ID_SWITCH, 0, R.string.menu_title_switch);
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItem item = menu.add(0, ITEM_ID_BACKUP, 0, R.string.menu_title_backup);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if (id == ITEM_ID_SWITCH) {
            if (mCurrentFragment == FRAGMENT_DAILY) {
                setFragment(FRAGMENT_BRACES);
            } else if (mCurrentFragment == FRAGMENT_BRACES) {
                setFragment(FRAGMENT_DAILY);
            }
            return true;
        } else */if (id == ITEM_ID_BACKUP) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
            MyRepositoryService.startActionBackup(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveFinished(MessageEvent messageEvent) {
        Log.i(TAG, "onSaveFinished: " + messageEvent.getMessage() + ",finish=" + messageEvent.isFinish());
        if (messageEvent.isFinish() && MyRepositoryService.MESSAGE_SAVE.equals(messageEvent.getMessage())) {
            Toast.makeText(this, "Save finished!", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_BRACES:
                    return new BracesListFragment();
                case FRAGMENT_DAILY:
                    return new DailyListFragment();
                default:
                    throw new RuntimeException("position " + position + " invalid");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
