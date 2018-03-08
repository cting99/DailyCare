package cting.com.robin.support.teethcare.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import cting.com.robin.support.teethcare.braces.BracesRecord;
import cting.com.robin.support.teethcare.daily.DailyRecord;

public abstract class DataGenerator {

    protected static String TAG = "cting/";
    protected static ArrayList<DailyRecord> mDailyList = new ArrayList<>();
    protected static ArrayList<BracesRecord> mBracesList = new ArrayList<>();

    public DataGenerator() {
    }

    protected void initIfNeeded(Context context) {
        if (mDailyList.size() == 0 || mBracesList.size() == 0) {
            mDailyList.clear();
            mBracesList.clear();
            Log.i(TAG + getClass().getSimpleName(), "forceLoad begin {");
            forceLoad(context);
            Log.i(TAG + getClass().getSimpleName(), "forceLoad end    }");
            Collections.sort(mDailyList);
            Collections.sort(mBracesList);
        }
    }

    protected void forceLoad(Context context) {
        // do loading
    }

    public boolean checkEmpty(Context context) {
        Log.i(TAG + getClass().getSimpleName(), "before----checkEmpty: daily size=" + mDailyList.size()
                + ", braces size=" + mBracesList.size());
        initIfNeeded(context);
        Log.i(TAG + getClass().getSimpleName(), "after-----checkEmpty: daily size=" + mDailyList.size()
                + ", braces size=" + mBracesList.size());
        return mDailyList.size() == 0 || mBracesList.size() == 0;
    }

    public ArrayList<DailyRecord> getDailyRecords(Context context) {
        initIfNeeded(context);
        return mDailyList;
    }

    public ArrayList<BracesRecord> getBracesRecords(Context context) {
        initIfNeeded(context);
        return mBracesList;
    }

    public BracesRecord getLastBraces(Context context) {
        if (mBracesList.size() > 0) {
            return mBracesList.get(0);//index 0 is the first one, see BracesRecord$compareTo()
        }
        return null;
    }

    public void updateLastBraces(String date) {
        if (mBracesList.size() > 0) {
//            mBracesList.get(0).setToDate(date);
            //TODO
        }
    }

    public void addBraces(BracesRecord braces) {
        mBracesList.add(0, braces);
    }


    public DailyRecord getLastDay(Context context) {
        if (mDailyList.size() > 0) {
            return mDailyList.get(0);//index 0 is the first one, see DailyRecord$compareTo()
        }
        return null;
    }

    public DailyRecord getDayByDate(String string) {
        for (DailyRecord dailyRecord : mDailyList) {
            if (TextUtils.equals(dailyRecord.getDate(), string)) {
                return dailyRecord;
            }
        }
        return null;
    }

    public void addDay(DailyRecord today) {
        mDailyList.add(0, today);//add at index 0 for this descending ordered list
        updateLastBraces(today.getDate());
    }

    public void deleteDay(int dayIndex) {
        int count = mDailyList.size();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                if (mDailyList.get(i).getIndex() == dayIndex) {
                    mDailyList.remove(i);
                    count--;
                }
            }
            /*count = mBracesList.size();
            for (int i = 0; i < count; i++) {

            }*/
        }

    }
}
