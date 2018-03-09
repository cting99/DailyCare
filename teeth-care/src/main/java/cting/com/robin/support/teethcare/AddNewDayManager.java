package cting.com.robin.support.teethcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;
import cting.com.robin.support.teethcare.database.SourceDatabase;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class AddNewDayManager implements DialogInterface.OnClickListener {
    private static final boolean ALWAYS_ASK = true;
    private int mCurrentDayIndex;
    private String mTodayDate;
    private int mLastBracesIndex;
    private DailyRecord mToday;

    private SourceDatabase mDBSource;
    private Context mContext;

    public AddNewDayManager(Context context) {
        mContext = context;
    }

    public void gotoToday() {
//        Toast.makeText(this, "goto today!", Toast.LENGTH_SHORT).show();
        mDBSource = new SourceDatabase(mContext);
        mDBSource.open();
        BracesRecord lastBraces = mDBSource.queryLastBraces(mContext);
        DailyRecord lastDay = mDBSource.queryLastDay(mContext);
        mDBSource.close();
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
        } else if (lastBraces.getDayCount() >= 8 || ALWAYS_ASK) {
            //check if current braces finished
            mLastBracesIndex = lastBraces.getIndex();
            mCurrentDayIndex = lastDay.getIndex() + 1;
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle("Do you want to use a new braces today?")
                    .setPositiveButton(android.R.string.ok, this)
                    .setNegativeButton(android.R.string.cancel, this);
            builder.create().show();
        } else {
            //new today
            mLastBracesIndex = lastBraces.getIndex();
            mCurrentDayIndex = lastDay.getIndex() + 1;
            addNewDay(mCurrentDayIndex);
            Toast.makeText(mContext, "It's a new day!", Toast.LENGTH_SHORT).show();
        }
        goNow();
    }

    public void goNow() {
        if (mToday != null) {
            DailyDetailActivity.launch(mContext, mToday, true);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            //new today in a new braces
            addNewBraces(mLastBracesIndex);
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            //continue add aday
            addNewDay(mCurrentDayIndex);
        }
        goNow();
    }

    public void addFirstBraces() {
        addNewBraces(1);
    }

    public BracesRecord addNewBraces(int index) {
        BracesRecord braces = new BracesRecord();
        braces.setIndex(index);
        braces.setDate(mTodayDate);
        braces.setTotalTime("0h");
        mDBSource.open();
        mDBSource.insertBraces(braces);
        mDBSource.close();
        Log.d(MainActivity.TAG, "addNewBraces: " + braces);

        addNewDay(mCurrentDayIndex);
        return braces;
    }

    public DailyRecord addNewDay(int index) {
        mToday = new DailyRecord();
        mToday.setIndex(index);
        mToday.setLine("00:00,00:00");
        mToday.setTotalTime("0h");
        mToday.setDate(mTodayDate);

        mDBSource.open();
        mDBSource.insertDaily(mToday);
        mDBSource.close();
        Log.d(MainActivity.TAG, "addNewDay: " + mToday);

//        MyRepositoryService.startActionSave(mContext);
        return mToday;
    }
}