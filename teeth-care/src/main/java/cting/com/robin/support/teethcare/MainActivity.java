package cting.com.robin.support.teethcare;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import cting.com.robin.support.teethcare.repository.MessageEvent;
import cting.com.robin.support.teethcare.repository.MyRepositoryService;
import cting.com.robin.support.teethcare.utils.GsonHelper;

public class MainActivity extends BasePermissionCheckActivity {
    private static final String FRAGMENT_DAILY = "fragment_daily";
    private static final String FRAGMENT_BRACES = "fragment_braces";
    private static final int ITEM_ID_SWITCH = 0;
    private static final int ITEM_ID_BACKUP = 1;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyRepositoryService.startActionCheckDatabase(this);
    }

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

        item = menu.add(0, ITEM_ID_BACKUP, 0, R.string.menu_title_backup);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == ITEM_ID_SWITCH) {
            if (mCurrentFragment == FRAGMENT_DAILY) {
                setFragment(FRAGMENT_BRACES);
            } else if (mCurrentFragment == FRAGMENT_BRACES) {
                setFragment(FRAGMENT_DAILY);
            }
            return true;
        } else if (id == ITEM_ID_BACKUP) {
            EventBus.getDefault().register(this);
            MyRepositoryService.startActionSave(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveFinished(MessageEvent messageEvent) {
        Log.i(TAG, "onSaveFinished: " + messageEvent.getMessage() + ",finish=" + messageEvent.isFinish());
        if (messageEvent.isFinish() && MyRepositoryService.MESSAGE_SAVE.equals(messageEvent.getMessage())) {
            Toast.makeText(this, "Save finished!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFragment(String fragment) {
        if (FRAGMENT_DAILY.equals(fragment)) {
            if (mDailyFragment == null) {
                mDailyFragment = new DailyListFragment();
                Log.w(TAG, "setFragment: new DailyListFragment");
            }
            setFragment(mDailyFragment);
            mCurrentFragment = fragment;
        } else if (FRAGMENT_BRACES.equals(fragment)) {
            if (mBracesFragment == null) {
                mBracesFragment = new BracesListFragment();
                Log.w(TAG, "setFragment: new BracesListFragment");
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
