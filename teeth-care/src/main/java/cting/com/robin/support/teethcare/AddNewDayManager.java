package cting.com.robin.support.teethcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;
import cting.com.robin.support.teethcare.daily.detail.DailyDetailActivity;
import cting.com.robin.support.teethcare.database.SourceDatabase;
import cting.com.robin.support.teethcare.utils.TimeFormatHelper;

public class AddNewDayManager implements DialogInterface.OnClickListener {
    private static final boolean ALWAYS_ASK = true;
    public static final String TAG = "cting/NewDay";
    private SourceDatabase mDBSource;
    private Context mContext;

    public AddNewDayManager(Context context) {
        mContext = context;
    }

    public void gotoToday() {
        mDBSource = new SourceDatabase(mContext);
        DailyRecord lastDay = mDBSource.queryLastDay();
        String lastDate = lastDay.getDate();
        String todayDate = TimeFormatHelper.formatToday();
        int dayIndex = -1;
        if (TimeFormatHelper.isSameDay(lastDate, todayDate)) {
            //today exist
            dayIndex = lastDay.getIndex();
        } else {
            //create today and all absent days between today and last day
            Log.w(TAG, "gotoToday: insert buntch of days from " + lastDate + " to " + todayDate);
            Toast.makeText(mContext, "insert buntch of days from " + lastDate + " to " + todayDate, Toast.LENGTH_SHORT).show();
            dayIndex = mDBSource.insertAbsentItems(lastDate, todayDate);
        }
        goNow(dayIndex);
    }

    public void goNow(int dayIndex) {
        if (dayIndex >= 0) {
            DailyDetailActivity.launch(mContext, dayIndex, true);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
/*
    public void gotoToday() {
//        Toast.makeText(this, "goto today!", Toast.LENGTH_SHORT).show();
        mDBSource = new SourceDatabase(mContext);
        mDBSource.open();
        //TODO
        BracesRecord lastBraces = null;//mDBSource.queryLastBraces(mContext);
        DailyRecord lastDay = null;//mDBSource.queryLastDay(mContext);
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
//        braces.setDate(mTodayDate);
//        braces.setTotalTime("0h");
        //TODO
//        mDBSource.open();
//        mDBSource.insertBraces(braces);
//        mDBSource.close();
        Log.d(MainActivity.TAG, "addNewBraces: " + braces);

        addNewDay(mCurrentDayIndex);
        return braces;
    }

    public DailyRecord addNewDay(int index) {
        mToday = new DailyRecord();
        mToday.setIndex(index);
//        mToday.setLine("00:00,00:00");
        mToday.setTotalTime("0h");
        mToday.setDate(mTodayDate);

        //TODO
//        mDBSource.open();
//        mDBSource.insertDaily(mToday);
//        mDBSource.close();
        Log.d(MainActivity.TAG, "addNewDay: " + mToday);

//        MyRepositoryService.startActionBackup(mContext);
        return mToday;
    }*/
}